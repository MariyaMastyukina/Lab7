package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.CollectWorker;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды history-вывод последних 8 команд
 */
public class HistoryCommand implements Command {
    static Logger LOGGER;
    private CollectWorker coll;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param p- переменная для управления командами
     */
    private ControlUnit pusk;
    public HistoryCommand(ControlUnit p, CollectWorker coll){
        p.addCommand("history",this);
        this.pusk=p;
        this.coll=coll;
        LOGGER=Logger.getLogger(HistoryCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        return coll.history(pusk);
    }
}
