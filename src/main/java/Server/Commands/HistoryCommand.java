package Server.Commands;

import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды history-вывод последних 8 команд
 */
public class HistoryCommand implements Command {
    static Logger LOGGER;
    private CityService cityService;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param p- переменная для управления командами
     */
    private ControlUnit controlUnit;

    public HistoryCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand("history", this);
        this.controlUnit = controlUnit;
        this.cityService = cityService;
        LOGGER = AppUtils.initLogger(HistoryCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        return cityService.history(controlUnit);
    }
}
