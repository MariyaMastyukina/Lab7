package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;
import Server.CollectionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды clear-очистка коллекции
 */
public class ClearCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public ClearCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("clear",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(ClearCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        String result = CollectionDB.clearDB(user.getLogin());
        if (coll.getSize() == 0) {
            return "Команда clear не выполнена. Коллекция пуста";
        } else {
            coll.clear(user.getLogin());
            if (result.isEmpty()) {
                return "Команда clear выполнена. Коллекция очищена";
            } else {
                return "Команда clear выполнена, но было отказано в доступе к объектам с именами и id: \n" + result;
            }
        }
    }
    }
