package Server.Commands;

import Client.CommandObject;
import Server.Collection.ControlUnit;
import Server.UserDB;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CheckInCommand implements Command{
    public CheckInCommand(ControlUnit pusk){
        pusk.addCommand("check_in",this);
    }
    @Override
    public String execute(CommandObject CO) throws IOException, SQLException, NoSuchAlgorithmException {
        return UserDB.check_in(CO.getLogin(),CO.getPassword());
    }
}