package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Collection.City;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.DBUtils.CollectionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Класс команды update-обновление элемента по id
 */
public class UpdateCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param p-          переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public UpdateCommand(ControlUnit p, CollectWorker collection) {
        p.addCommand("update", this);
        this.coll = collection;
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject CO) throws IOException, SQLException {
        System.out.println(CO.getArgs().size());
        String checker = null;
        long id = Long.parseLong(CO.getOption());
        for (City city : coll.getCollection()) {
            if (city.getIdOfCity() == id) {
                City newcity = new City(CO.getArgs());
                newcity.setId(id);
                newcity.setUser(CO.getLogin());
                checker = CollectionDB.UpdateIDDB(id, newcity);
                if (checker == null) {
                    coll.update(newcity, CO.getLogin());
                    return "Команда update выполнена. Значение элемента коллекции с id " + Integer.parseInt(CO.getOption()) + " обновлено, введите команду \"show\", чтобы увидеть содержимое коллекции";
                }
            }
            else{
                return checker;
            }
        }
        return "Элемента с таким id нет. Введите команду \"show\", чтобы увидеть элементы коллекции и их id.";
    }
}