package Client;

import Utils.ConnectionUtils.ServerConnectionUtils;
import Utils.DataUtils.CommandUtils;
import Utils.DataUtils.TransferUtils;
import Client.IOClient.IOInterfaceStream;
import Client.IOClient.IOTerminal;
import Utils.AppUtils;
import Utils.UserUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве клиента
 */
public class Client {
    private static Logger LOGGER;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        LOGGER = AppUtils.initLogger(Client.class, true);
        IOInterfaceStream ioClient = new IOTerminal(System.in, System.out);
        ServerConnectionUtils serverConnectionUtils = new ServerConnectionUtils();
        LOGGER.log(Level.INFO, "Начало работы клиента");
        serverConnectionUtils.connection(ioClient);
        IOInterfaceStream ioServer = new IOTerminal(serverConnectionUtils.getInputStream(), serverConnectionUtils.getOutputStream());
        LOGGER.log(Level.INFO, "Ждем готовности сервера");
        LOGGER.log(Level.INFO, "Получаем спсиок команд с сервера");
        TransferUtils transferUtils = new TransferUtils(ioServer, ioClient, serverConnectionUtils);
        UserUtils userUtils = UserUtils.createUser(ioClient, ioServer);
        ioClient.writeln("Здравстуйте! Введите \"help\", чтобы увидеть список доступных команд");
        ioClient.writeln("Введите команду");
        LOGGER.log(Level.INFO, "Считываем команду с консоли");
        while (true) {
            try {
                CommandUtils command;
                String line = ioClient.readLine();
                LOGGER.log(Level.INFO, "Создаем объект текущей команды");
                if (line.equals("exit")) {
                    userUtils = UserUtils.createUser(ioClient, ioServer);
                    ioClient.writeln("Здравстуйте! Введите \"help\", чтобы увидеть список доступных команд");
                    ioClient.writeln("Введите команду");
                    continue;
                }
                command = new CommandUtils(line, null);
                command.setLogin(userUtils.getLogin());
                command.setPassword(userUtils.getPassword());
                if (command.getChecker()) {
                    try {
                        LOGGER.log(Level.INFO, "Запускаем метод отправки командв на севрер");
                        transferUtils.transfer(command);
                    } catch (StackOverflowError e) {
                        ioClient.writeln("Произошла зацикливнаие команды execute_script. Выполнение команды прекращено");
                    }
                }
                ioClient.writeln("Введите команду");
                LOGGER.log(Level.INFO, "Считываем команду с консоли");
            } catch (NullPointerException e) {
                ioClient.writeln("Введите команду еще раз");
            }
        }
    }
}