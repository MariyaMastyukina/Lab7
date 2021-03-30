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
 * Класс команды remove_all_by_meters_above_sea_level-удаление элементов коллекции с данным полем metersAboveSeaLevel
 */
public class RemoveAllBYMetersAboveSeaLevelCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public RemoveAllBYMetersAboveSeaLevelCommand(CollectWorker collection, ControlUnit p){
        p.addCommand("remove_all_by_meters_above_sea_level",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(RemoveAllBYMetersAboveSeaLevelCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException, SQLException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        String result=CollectionDB.removeMASLBD(Integer.parseInt(user.getOption()),user.getLogin());
        String answer=coll.remove_by_meters_above_sea_level(Integer.parseInt(user.getOption()),user.getLogin());

        if(!result.isEmpty()) {
            return "Команда remove_all_by_meters_above_sea_level выполнена, но вы не смогли удалить следующие объекты из-за отказа в доступе:\n"+result;
        }
        return answer;
    }
}
