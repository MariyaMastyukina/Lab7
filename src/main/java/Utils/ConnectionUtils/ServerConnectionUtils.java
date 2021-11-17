package Utils.ConnectionUtils;

import Client.IOClient.IOInterfaceStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Класс для соединения клиента с сервером
 */
public class ServerConnectionUtils {
    Socket socket;

    /**
     * Функция соединения клиента с сервером
     * @param ioClient
     */
    public Socket connection(IOInterfaceStream ioClient) throws IOException {
        while(true) {
            try {
                //создает сокет, соединение с сервером
                String PORT = readPort(ioClient);
                if (PORT == null) {
                    ioClient.writeln("Пока!");
                    System.exit(0);
                }
                socket = new Socket("localhost", Integer.parseInt(PORT));
                System.out.println("Соединение с сервером установлено");
                return socket;
            } catch (ConnectException | UnknownHostException e) {
                System.out.println("Не удалось установить соединение с сервером! Попробуйте ввести другой порт или exit для заверщения: ");
            }
        }
    }
    /**
     * Функция получения потока ввода
     */
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }
    /**
     * Функция получения потока вывода
     */
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    /**
     * Функция закрытия сокета
     */
    public void close() throws IOException {
        socket.close();
    }
    private String readPort(IOInterfaceStream ioClient) throws IOException {
        ioClient.writeln("Введите порт для подключения к серверу или команду exit: ");
        String PORT = ioClient.readLine();
        if (PORT.equalsIgnoreCase("exit"))
            return null;
        while (!PORT.matches("[0-9]{4}")) {
            ioClient.writeln("Неверный формат!");
            ioClient.writeln("Введите порт для подключения к серверу или команду exit: ");
            PORT = ioClient.readLine();
        }
        return PORT;
    }

}
