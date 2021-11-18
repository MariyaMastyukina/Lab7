package Server;

import Server.Launch.ServerLaunch;
import Utils.ConnectionUtils.ReadRequestUtils;
import Utils.ConnectionUtils.SendResponseUtils;
import Server.Launch.CityService;
import Server.Launch.CommandLaunch;

import java.io.IOException;
import java.net.ConnectException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.logging.Level;

/**
 * @author Мастюкина Мария
 * Класс, работающий в качестве сервера
 */
public class Server {


    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerLaunch.startServer();
    }
}
