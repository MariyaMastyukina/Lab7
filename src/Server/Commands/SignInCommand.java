package Server.Commands;

import Client.CommandObject;
import Server.Collection.ControlUnit;
import Server.UserDB;

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
