package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Collection.City;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.DBUtils.CollectionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды add-добавление объекта в коллекцию
 */
public class AddCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param p-переменная для управления командами
     * @param collection- переменная для работы с коллекцией
     */
    public AddCommand(ControlUnit p, CollectWorker collection) {
        p.addCommand("add", this);
        this.coll = collection;
        LOGGER=Logger.getLogger(AddCommand.class.getName());
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        City city=new City(user.getArgs());
        city.setUser(user.getLogin());
        CollectionDB.insertColl(city,false,user.getLogin());
        coll.add(city);
        return "Команда add выполнена. Элемент добавлен в коллекцию, введите команду \"show\", чтобы увидеть содержимое коллекции";

    }
}
