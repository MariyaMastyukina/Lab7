package Server.Commands;

import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExitCommand implements Command {
    CityService cityService;
    static Logger LOGGER;
    ControlUnit controlUnit;

    public ExitCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        this.cityService = cityService;
        this.controlUnit = controlUnit;
        controlUnit.addCommand("exit", this);
        LOGGER = AppUtils.initLogger(ExitCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        StringBuilder sb = new StringBuilder();
        controlUnit.clearListCommand();
        sb.append("Команда exit выполняется. Завершение работы программы").append("\n").append("exit");
        return sb.toString();
    }
}
