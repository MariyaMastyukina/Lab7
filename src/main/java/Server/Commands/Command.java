package Server.Commands;

import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * {@code Server.Server.Server.Commands.Command} содержит команду выполнения команд
 */
public interface Command {
    /**
     * Функция выполнения команды
     */
    String execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException;
}
