package Server.Commands;

import Server.Collection.*;
import Utils.DataUtils.CommandUtils;
import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Server.DBUtils.CityDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Класс команды update-обновление элемента по id
 */
public class UpdateCommand implements Command {
    CityService cityService;
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit- переменная для управления командами
     * @param cityService- переменнаяи для работы с коллекцией
     */
    public UpdateCommand(ControlUnit controlUnit, CityService cityService) {
        controlUnit.addCommand("update", this);
        this.cityService = cityService;
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException {
        City newCity = updateCity(commandUtils.getArgs(), commandUtils.getLogin(), Long.parseLong(commandUtils.getOption()));
        return cityService.update(newCity);
    }

    private City updateCity(List<String> args, String login, long id) {
        City city = new City();
        city.setId(id);
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