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
 * Класс команды clear-очистка коллекции
 */
public class ClearCommand implements Command {
    CityService cityService;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit- переменная для управления командами
     * @param cityService-  переменнаяи для работы с коллекцией
     */
    public ClearCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand("clear", this);
        this.cityService = cityService;
        LOGGER = AppUtils.initLogger(ClearCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        String result = cityService.clear(commandUtils.getLogin());
        if (result.isEmpty()) {
            return "Команда clear выполнена. Элементы удалены";
        } else {
            return "Команда clear выполнена, но было отказано в доступе к объектам с именами и id: \n" + result;
        }
    }
}
