package Server.Commands;

import Utils.DataUtils.CommandUtils;
import Server.Launch.ControlUnit;
import Server.DBUtils.UserDAO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignInCommand implements Command {
    public SignInCommand(ControlUnit controlUnit) {
        controlUnit.addCommand("sign_in", this);
    }

    @Override
    public String execute(CommandUtils commandUtils) throws IOException, SQLException, NoSuchAlgorithmException {
        return UserDAO.signIn(commandUtils.getLogin(), commandUtils.getPassword());
    }
}
