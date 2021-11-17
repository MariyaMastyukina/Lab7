package Server.Commands;

import Utils.DataUtils.CommandUtils;
import Server.Launch.ControlUnit;
import Server.DBUtils.UserDAO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CheckInCommand implements Command {
    public CheckInCommand(ControlUnit controlUnit) {
        controlUnit.addCommand("check_in", this);
    }

    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException {
        return UserDAO.checkIn(commandUtils.getLogin(), commandUtils.getPassword());
    }
}
