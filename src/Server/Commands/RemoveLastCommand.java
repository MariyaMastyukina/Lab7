package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;
import Server.DBUtils.CollectionDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды remove_last-Удаление последнего элемента
 */
public class RemoveLastCommand implements Command {
    private CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public RemoveLastCommand(ControlUnit p, CollectWorker collection){
        p.addCommand("remove_last",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(RemoveByIdCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        if (coll.getSize()!=0){
            long id=coll.get_id_last(user.getLogin());
            if (id!=0) {
                CollectionDB.removeIDBD(id, user.getLogin());
                coll.remove_last(user.getLogin());
                return "Команда remove_last выполнена. Последний элемент коллекции удален";
            }
            else{
                return "Команда remove_last не выполнена, так как вы не создали ни один объект";
            }
    }
        else {
            return "Команда remove_last не выполнена. Коллекция пуста, удаление невозможно";
        }
        }
}
