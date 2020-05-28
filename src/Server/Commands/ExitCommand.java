package Server.Commands;

import Client.CommandObject;
import Server.Collection.CollectWorker;
import Server.Collection.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExitCommand implements Command {
    CollectWorker coll;
    static Logger LOGGER;
    ControlUnit p;
    public ExitCommand(ControlUnit p, CollectWorker collection) {
        this.coll=collection;
        this.p=p;
        p.addCommand("exit",this);
        LOGGER=Logger.getLogger(ClearCommand.class.getName());
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandObject user) throws IOException {
        LOGGER.log(Level.INFO,"Отправка результата выполнения команды на сервер");
        StringBuilder sb=new StringBuilder();
        coll.clear(user.getLogin());
        p.clearListCommand();
        sb.append("Команда exit выполняется. Завершение работы программы").append("\n").append("exit");
        return sb.toString();
    }
}
