package Utils.DataUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

/**
 * Класс-обертка для команды
 */
public class CommandUtils implements Serializable {
    private String login;
    private char[] password;
    /**
     * Поле имя команды
     */
    private String nameCommand;
    /**
     * Поле аргумент команды
     */
    private String option;
    /**
     * Поле валидности введенной команды
     */
    private Boolean checker = false;
    /**
     * Поле список аргументов для команд add и update
     */
    private List<String> args;
    private transient Scanner scanner = new Scanner(System.in);

    /**
     * Конструктор - создания команды и проверка валидности ее ввода
     *
     * @param line     -строка с консоли
     * @param filename -имя файла execute_script
     */
    public CommandUtils(String line, String filename) {
        if (!line.trim().equals("")) {
            if (line.contains(" ")) {
                String[] lineParts = line.split(" ", 2);
                checker = ValidationUtils.isValidCommand(lineParts[0], lineParts[1]);
                if (checker) {
                    nameCommand = lineParts[0];
                    option = lineParts[1];
                }
            } else {
                checker = ValidationUtils.isValidCommand(line, null);
                if (checker) {
                    nameCommand = line;
                    option = null;
                }
            }
        }
        if (nameCommand != null && (filename == null)) {
            if (nameCommand.equals("add") || nameCommand.equals("update")) {
                setListArgs();
            }
        }

    }

    /**
     * Функция создания списка аргументов для команд add и update
     */
    private void setListArgs() {
        setArgs(ValidationUtils.readArgsCity(scanner));
    }

    /**
     * Функция заполнения списка аргументов для команд add и update существующим
     */
    public void setArgs(List<String> args) {
        this.args = args;
    }

    /**
     * Функция получения значения поля {@link CommandUtils#nameCommand}
     *
     * @return имя команды
     */
    public String getNameCommand() {
        return nameCommand;
    }

    /**
     * Функция получения значения поля {@link CommandUtils#args}
     *
     * @return список аргументов для команд add и update
     */
    public List<String> getArgs() {
        return args;
    }

    /**
     * Функция получения значения поля {@link CommandUtils#option}
     *
     * @return аргумент команды
     */
    public String getOption() {
        return option;
    }

    /**
     * Функция получения значения поля {@link CommandUtils#checker}
     *
     * @return валидность команды
     */
    public Boolean getChecker() {
        return checker;
    }

    public char[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
