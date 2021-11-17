package Utils.DataUtils;

import Client.IOClient.IOInterfaceStream;
import Utils.AppUtils;
import Utils.ConnectionUtils.ServerConnectionUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для отправки на сервер объекта и получения ответа
 */
public class TransferUtils {
    private static Logger LOGGER;
    private String answer = null;

    private IOInterfaceStream ioServer;
    private IOInterfaceStream ioClient;
    private ServerConnectionUtils serverConnectionUtils;

    public TransferUtils(IOInterfaceStream ioServer, IOInterfaceStream ioClient, ServerConnectionUtils serverConnectionUtils) throws IOException {
        this.ioServer = ioServer;
        this.ioClient = ioClient;
        this.serverConnectionUtils = serverConnectionUtils;
        LOGGER = AppUtils.initLogger(TransferUtils.class, true);
    }

    /**
     * Функция для отправки на сервер объекта и получения ответа
     *
     * @param command-команда
     */
    public String transfer(CommandUtils command) throws IOException, ClassNotFoundException {
        LOGGER.log(Level.INFO, "Проверяем команду на execute_script");
        if (command.getNameCommand().equals("execute_script")) {
            File file = new File(command.getOption());
            if (file.exists()) {
                if (file.canRead()) {
                    FileReader fileReader = new FileReader(file);
                    Scanner scanner = new Scanner(fileReader);
                    while (scanner.hasNextLine()) {
                        String fileLine = scanner.nextLine();
                        CommandUtils commandUtils = new CommandUtils(fileLine, command.getOption());
                        command.setLogin(command.getLogin());
                        if (commandUtils.getNameCommand().equals("add") || commandUtils.getNameCommand().equals("update")) {
                            ValidationUtils.readFileArgs(scanner);
                        }
                        if (commandUtils.getChecker()) {
                            transfer(commandUtils);
                        }
                    }
                } else {
                    ioClient.writeln("Чтение из файлa " + command.getOption() + " невозможно, выполнение команды execute_script невозможно. Измените права.");
                    return answer;
                }
            } else {
                ioClient.writeln("Файл не существует, выполнение команды execute_script невозможно");
                return answer;
            }
        } else {
            if (command.getChecker()) {
                LOGGER.log(Level.INFO, "Отправляем команду на сервер");
                ioServer.writeObj(command);

                long startTime = System.currentTimeMillis();
                LOGGER.log(Level.INFO, "Ждем готовности сервера");
                while (!ioServer.ready()) {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime > 20000) {
                        ioClient.writeln("Сервер недоступен, завершение работы программы");
                        serverConnectionUtils.close();
                        System.exit(0);
                    }
                }
                LOGGER.log(Level.INFO, "Получаем ответ сервера на отправленную команду");
                LOGGER.log(Level.INFO, "Выводим ответ сервера на консоль");
                while (ioServer.ready()) {
                    answer = ioServer.readLine();
                    if (answer.equals("exit")) {
                        ioServer.close();
                        serverConnectionUtils.close();
                        LOGGER.log(Level.INFO, "Завершение работы приложения");
                        System.exit(0);
                    }
                    ioClient.writeln(answer);
                }

            }
        }
        return answer;
    }
}
