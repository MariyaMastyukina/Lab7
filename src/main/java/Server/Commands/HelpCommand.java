package Server.Commands;

import Utils.AppUtils;
import Utils.DataUtils.CommandUtils;
import Server.Launch.ControlUnit;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс команды help-список команд
 */
public class HelpCommand implements Command {
    static Logger LOGGER;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param controlUnit- переменная для управления командами
     */
    public HelpCommand(ControlUnit controlUnit) throws IOException {
        controlUnit.addCommand("help", this);
        LOGGER = AppUtils.initLogger(HelpCommand.class, false);
    }

    /**
     * Функция выполнения команды
     */
    @Override
    public String execute(CommandUtils commandUtils) throws IOException {
        LOGGER.log(Level.INFO, "Отправка результата выполнения команды на сервер");
        return "check_in:регистрация" +
                "sign_in:вход" +
                "help:вывести справку по доступным командам" + "\n" +
                "show:вывести в стандартный поток вывода все элементы коллекции в строковом представлении" + "\n" +
                "add:добавить новый элемент в коллекцию" + "\n" +
                "update <id>:обновить значение элемента коллекции, id которого равен заданному" + "\n" +
                "remove_by_id <id>:удалить элемент из коллекции по его id" + "\n" +
                "execute_script <file_name>:считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме." + "\n" +
                "exit:завершить программу (без сохранения в файл)" + "\n" +
                "remove_last:удалить последний элемент из коллекции" + "\n" +
                "sort:отсортировать коллекцию в естественном порядке" + "\n" +
                "history:вывести последние 8 команд (без их аргументов)" + "\n" +
                "remove_all_by_meters_above_sea_level metersAboutSeaLevel:удалить из коллекции все элементы, значение поля metersAboveSeaLevel которого эквивалентно заданному" + "\n" +
                "group_counting_by_population:сгруппировать элементы коллекции по значению поля population, вывести количество элементов в каждой группе" + "\n" +
                "print_ascending:вывести элементы коллекции в порядке возрастания";
    }
}
