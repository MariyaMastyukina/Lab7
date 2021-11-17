package Server.Commands;

import Server.Collection.*;
import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды add-добавление объекта в коллекцию
 */
public class AddCommand implements Command {
    CityService cityService;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit-переменная для управления командами
     * @param cityService-  переменная для работы с коллекцией
     */
    public AddCommand(ControlUnit controlUnit, CityService cityService) throws IOException {
        controlUnit.addCommand("add", this);
        this.cityService = cityService;
        LOGGER = AppUtils.initLogger(AddCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        City city = createCity(commandUtils.getArgs(), commandUtils.getLogin());
        cityService.add(city, commandUtils.getLogin() );
        return "Команда add выполнена. Элемент добавлен в коллекцию, введите команду \"show\", чтобы увидеть содержимое коллекции";

    }

    private City createCity(List<String> args, String login) {
        City city = new City();
        city.setName(args.get(0));
        city.setCoordinates(new Coordinates(args.subList(1, 3)));
        city.setCreationDate(LocalDateTime.now());
        city.setArea(Double.parseDouble(args.get(3)));
        city.setPopulation(Integer.parseInt(args.get(4)));
        city.setMetersAboveSeaLevel(Integer.parseInt(args.get(5)));
        city.setCapital(Boolean.parseBoolean(args.get(6)));
        city.setClimate(Climate.valueOf(args.get(7)));
        city.setGovernment(Government.valueOf(args.get(8)));
        city.setGovernor(new Human(args.get(9)));
        city.setUser(login);
        return city;
    }
}
