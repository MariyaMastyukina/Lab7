package Server.Commands;

import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды show-вывод элементов коллекции
 */
public class ShowCommand implements Command {
    CityService cityService;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit- переменная для управления командами
     * @param cityService- переменнаяи для работы с коллекцией
     */
    public ShowCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand("show", this);
        this.cityService = cityService;
        LOGGER = AppUtils.initLogger(ShowCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        return cityService.show();
    }
}
