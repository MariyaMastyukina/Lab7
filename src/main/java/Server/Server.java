package Server;

import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Client.IOClient.IOInterfaceStream;
import Client.IOClient.IOTerminal;
import Utils.ConnectionUtils.ClientConnectionUtils;
import Utils.ConnectionUtils.ReadRequestUtils;
import Utils.ConnectionUtils.SendResponseUtils;
import Server.DBUtils.CityDAO;
import Server.DBUtils.DBConnection;
import Server.DBUtils.UserDAO;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Server.Launch.Launch;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве сервера
 */
public class Server {
    private static Properties props = new Properties();
    private static ControlUnit controlUnit = new ControlUnit();
    private static Integer PORT = null;
    private static ForkJoinPool readRequest = ForkJoinPool.commonPool();
    private static ExecutorService handleRequest = Executors.newCachedThreadPool();
    private static Logger LOGGER;
    private static String URL;
    private static String login;
    private static String password;
    private static CityService cityService;

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        cityService = new CityService();
        props.load(Server.class.getClassLoader().getResourceAsStream("project.properties"));
        login = props.getProperty("DB.USERNAME");
        URL = props.getProperty("DB.URL");
        password = System.getProperty("DB.PASSWORD");
        ClientConnectionUtils clientConnectionUtils = new ClientConnectionUtils();
        IOInterfaceStream ioServer = new IOTerminal(System.in, System.out);
        LOGGER = AppUtils.initLogger(Server.class, false);
        try {
            LOGGER.log(Level.INFO, "Подключение к клиенту");
            PORT = clientConnectionUtils.connect(ioServer, PORT);
            ioServer.writeln("Порт сервера: " + PORT);
            ioServer.writeln("Ожидается подключение клиента");
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Ошибка в подключении к клиенту, не указан порт");
            System.out.println("Нужно указать порт");
            System.exit(0);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Ошибка в подключении к клиенту, формат порта указан неверно");
            System.out.println("Формат порта не верен");
            System.exit(0);
        }
        HashMap<SelectionKey, Future<CommandUtils>> to = new HashMap<>();
        HashMap<SelectionKey, Future<String>> res = new HashMap<>();
        DBConnection dbConnection = new DBConnection();
        CityDAO cityDAO = new CityDAO(dbConnection.connectDB(URL, login, password), "CITIES");
        UserDAO userDAO = new UserDAO(dbConnection.connectDB(URL, login, password), "USERS");
        while (true) {
            try {
                if (ioServer.ready()) {
                    if (ioServer.readLine().equals("help")) {
                        System.out.println("help:вывести справку по доступным командам\n" +
                                "info:вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                                "add:добавить новый элемент в коллекцию\n" +
                                "update id :обновить значение элемента коллекции, id которого равен заданному\n" +
                                "remove_by_id id:удалить элемент из коллекции по его id\n" +
                                "clear:очистить коллекцию\n" +
                                "save:сохранить коллекцию в файл\n" +
                                "execute_script file_name:считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                                "exit:завершить программу (без сохранения в файл)\n" +
                                "remove_last:удалить последний элемент из коллекции\n" +
                                "sort:отсортировать коллекцию в естественном порядке\n" +
                                "history:вывести последние 8 команд (без их аргументов)\n" +
                                "remove_all_by_meters_above_sea_level metersAboutSeaLevel:удалить из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному\n" +
                                "group_counting_by_population:сгруппировать элементы коллекции по значению поля population, вывести количество элементов в каждой группе\n" +
                                "print_ascending:вывести элементы коллекции в порядке возрастания");
                    }

                }
            } catch (NullPointerException e) {
                System.out.println("Завершение работы сервера");
                System.exit(1);
            }
            //к чему готов канал, получаем доступ к селектору
            clientConnectionUtils.getSelector().selectNow();
            Iterator<SelectionKey> iterator = clientConnectionUtils.getSelector().selectedKeys().iterator();
            if (iterator.hasNext()) {
                LOGGER.log(Level.INFO, "Получение текущего селектора");
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();
                try {
                    LOGGER.log(Level.INFO, "Проверка ключа селектора");
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isWritable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        LOGGER.log(Level.INFO, "Запуск полученный от клиента команды");
                        if (to.containsKey(selectionKey) && to.get(selectionKey).isDone()) {
                            res.put(selectionKey, handleRequest.submit(new Launch(cityService, to.get(selectionKey).get(), controlUnit)));
                            to.remove(selectionKey);
                        }
                        if (res.containsKey(selectionKey) && res.get(selectionKey).isDone()) {
                            new SendResponseUtils(channel).sendResponse(res.get(selectionKey).get());
                            res.remove(selectionKey);
                            LOGGER.log(Level.INFO, "Ответ отправлен клиенту");
                            selectionKey.interestOps(SelectionKey.OP_READ);
                        }

                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        LOGGER.log(Level.INFO, "Чтение полученной от клиента команды");
                        to.put(selectionKey, readRequest.submit(new ReadRequestUtils(channel)));
                        selectionKey.interestOps(SelectionKey.OP_WRITE);

                    }
                    if (selectionKey.isAcceptable()) {
                        LOGGER.log(Level.INFO, "Разрешение подключение клиента к севреру");
                        clientConnectionUtils.acceptConnection(selectionKey);
                    }
                } catch (ConnectException e) {
                    userDAO.closeConnection();
                    cityDAO.closeConnection();

                    System.out.println(e.getMessage());
                } catch (CancelledKeyException e) {
                    ioServer.writeln("Работа с текущем клиентом закончена. Ожидается подключение нового клиента");
                    clientConnectionUtils.sscClose();
                    clientConnectionUtils.connect(ioServer, PORT);
                    cityService = new CityService();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
