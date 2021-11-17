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
 * Класс команды remove_all_by_meters_above_sea_level-удаление элементов коллекции с данным полем metersAboveSeaLevel
 */
public class RemoveAllBYMetersAboveSeaLevelCommand implements Command {
    CityService cityService;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit- переменная для управления командами
     * @param cityService-      переменнаяи для работы с коллекцией
     */
    public RemoveAllBYMetersAboveSeaLevelCommand(CityService cityService, ControlUnit controlUnit) throws IOException {
        controlUnit.addCommand("remove_all_by_meters_above_sea_level", this);
        this.cityService = cityService;
        LOGGER = AppUtils.initLogger(RemoveAllBYMetersAboveSeaLevelCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        String result = cityService.removeByMetersAboveSeaLevel(Integer.parseInt(commandUtils.getOption()), commandUtils.getLogin());

        if (!result.isEmpty()) {
            return "Команда remove_all_by_meters_above_sea_level выполнена, но вы не смогли удалить следующие объекты из-за отказа в доступе:\n" + result;
        } else {
            return "Команда remove_all_by_meters_above_sea_level выполнена, все объекты с полем metersAboveSeaLevel, равным " + Integer.parseInt(commandUtils.getOption()) + " удалены";
        }
    }
}
