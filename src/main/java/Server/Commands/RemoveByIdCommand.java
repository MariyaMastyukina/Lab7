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
 * Класс команды remove_by_id-Удаление элементов по id
 */

public class RemoveByIdCommand implements Command {
    CityService cityService;
    static Logger LOGGER;
    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param controlUnit- переменная для управления командами
     * @param cityService- переменнаяи для работы с коллекцией
     */
    public RemoveByIdCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand("remove_by_id",this);
        this.cityService =cityService;
        LOGGER= AppUtils.initLogger(RemoveByIdCommand.class, false);
    }
    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        return cityService.removeById(Long.parseLong(commandUtils.getOption()), commandUtils.getLogin());
    }
}
