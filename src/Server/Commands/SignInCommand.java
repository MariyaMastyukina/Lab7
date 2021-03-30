package Server.Commands;

import Client.DataUtils.CommandObject;
import Server.Launch.ControlUnit;
import Server.DBUtils.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class SignInCommand implements Command {
    public SignInCommand(ControlUnit pusk){
        pusk.addCommand("sign_in",this);
    }
    @Override
    public String execute(CommandObject user) throws IOException, SQLException, NoSuchAlgorithmException {
        return UserDB.sign_in(user.getLogin(),user.getPassword());
    }
}
