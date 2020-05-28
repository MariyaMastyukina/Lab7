package Server.Commands;

import Client.CommandObject;

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
    String execute(CommandObject CO) throws IOException, SQLException, NoSuchAlgorithmException;
}
