package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды group_counting_by_population-группировка коллекции
 */
public class GroupCountingByPopulationCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     * @param collection- переменнаяи для работы с коллекцией
     */
    public GroupCountingByPopulationCommand(CollectWorker collection, ControlUnit p){
        p.addCommand("group_counting_by_population",this);
        this.coll=collection;
        LOGGER=Logger.getLogger(GroupCountingByPopulationCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return coll.group_counting_by_population();
    }
}
