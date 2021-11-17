package Utils.ConnectionUtils;

import Client.IOClient.IOInterfaceStream;
import Server.Server;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;

/**
 * Класс, отвечающий за соединение сервера с клиентом
 */
public class ClientConnectionUtils {
    ServerSocketChannel ssc;
    Selector selector;
    SocketChannel channel;
    /**
     * Конструктор соединения с клиентом
     * @param ioServer-I/O Server
     */
    public int connect(IOInterfaceStream ioServer, Integer PORT) throws IOException {
        //создаем канал сервер сокета
        if (PORT == null)
            PORT = Integer.parseInt(readPort(ioServer));
        while (true) {
            try {
                ssc=ServerSocketChannel.open();
                ssc.configureBlocking(false);
                //создаем адрес сокета и связываем его
                ssc.socket().bind(new InetSocketAddress(PORT));
                //создаем селектор
                selector=Selector.open();
                //регистрируем
                ssc.register(selector,SelectionKey.OP_ACCEPT);
                break;
            } catch (BindException e) {
                PORT++;
            }
        }
        return PORT;

    }
    /**
     * Функция получения селектора
     * @return селектор
     */
    public Selector getSelector(){
        return selector;
    }
    /**
     * Функция подтверждения соединения с клиентом
     */
    public void acceptConnection(SelectionKey key) throws IOException {
        //принимаем соединение к сокету канала
        ServerSocketChannel sschannel=(ServerSocketChannel)key.channel();
        channel=sschannel.accept();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_READ);
    }
    /**
     * Функция получения текущего канала
     * @return канал
     */
    public void sscClose() throws IOException {
        ssc.socket().close();
    }
    private String readPort(IOInterfaceStream ioServer) throws IOException {
        ioServer.writeln("Введите порт сервера: ");
        String PORT = ioServer.readLine();
        while (!PORT.matches("[0-9]{4}")) {
            ioServer.writeln("Неверный формат!");
            ioServer.writeln("Введите порт сервера: ");
            PORT = ioServer.readLine();
        }
        return PORT;
    }
}
