package Server.Launch;

import Server.Collection.City;
import Server.Comparators.AreaComparartor;
import Server.Comparators.CityComparator;
import Server.Comparators.NameComparator;
import Utils.DBUtils.CityDAO;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 * Класс, работающий с коллекцией объектов класса Server.Server.Server.Commands.AddCommand.City со свойствами date, collection, file, creationDate, area, population, metersAboveSeaLevel, capital, climate, government, governor.
 */
public class CityService {
    /**
     * Функция очистки коллекции
     */
    public String clear(String user) throws SQLException {
        return CityDAO.deleteAll(user);
    }//для clear

    /**
     * Функция удаления последнего элемента коллекции
     */
    public String removeLast(String user) throws SQLException {
        if (getIdLast(user) != 0) {
            return CityDAO.removeById(getIdLast(user), user);
        }
        return "Удаление не выполнено! Не создано ни одного города.";
    }


    /**
     * Функция удаления определенного элемента
     *
     * @param index- индекс элемента
     */

    /**
     * Функция получения определенного элемента коллекции
     *
     * @param metersAboveSeaLevel- индекс элемента
     * @return объект класса Server.Server.Server.Commands.AddCommand.City
     */
    public String removeByMetersAboveSeaLevel(int metersAboveSeaLevel, String login) throws SQLException {
        return CityDAO.removeByMetersAboveSeaLevel(metersAboveSeaLevel, login);
    }

    /**
     * Функция группировки коллекции по полю population
     */
    public String groupCountingByPopulation() throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return "Команда group_counting_by_population не выполнена. Коллекция пуста, групировка элементов невозможна";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Команда group_counting_by_population выполнена. Количество элементов полученных групп элементов коллекции:").append("\n");
            Map<Integer, Long> collectionPerPopulation = list.stream().collect(groupingBy(City::getPopulation, counting()));
            sb.append(collectionPerPopulation.toString());
            return sb.toString();
        }
    }


    /**
     * Функция получения всех элементов коллекции
     */

    public String show() throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return "Команда show не выполнена. Коллекция пустая";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Команда show выполнена. Список элементов коллекции:").append("\n");
            list.stream().sorted(new NameComparator()).collect(Collectors.toCollection(LinkedList::new)).forEach(city -> {
                sb.append(city.toString()).append("\n");
            });
            return sb.toString();
        }
    }

    public String printAscending() throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return "Команда print_ascending не выполнена. Коллекция пуста, сортировка невозможна";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Команда print_ascending выполнена. Коллекция, отсортированная по возрастанию поля-население города:").append("\n");
            list.stream().sorted(new CityComparator()).collect(Collectors.toCollection(LinkedList::new)).forEach(city -> {
                sb.append(city.toString()).append("\n");
            });
            return sb.toString();
        }
    }

    public String sort() throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        if (list.size() == 0) {
            return "Команда sort не выполнена. Коллекция пуста, сортировка невозможна";
        } else {
            StringBuilder sb = new StringBuilder();
            list.sort(new AreaComparartor());
            sb.append("Команда sort выполнена. Коллекция отсортирована по возрастанию значения поля-площадь:").append("\n");
            list.forEach(city -> {
                sb.append(city.toString()).append("\n");
            });
            return sb.toString();
        }
    }

    public String history(ControlUnit controlUnit) {
        return controlUnit.getListCommand();
    }

    public void add(City city, String login) throws SQLException {
        CityDAO.createCity(city, false, login);
    }

    public String removeById(long id, String user) throws SQLException {
        return CityDAO.removeById(id, user);
    }

    private long getIdLast(String user) throws SQLException {
        CopyOnWriteArrayList<City> list = CityDAO.getAllCities();
        long max = 0;
        for (City c : list) {
            if (c.getIdOfCity() > max && c.getUser().equals(user)) {
                max = c.getIdOfCity();
            }
        }
        return max;
    }

    public String update(City newCity) throws SQLException {
        return CityDAO.updateId(newCity.getIdOfCity(), newCity);
    }
}
