package Utils;

import Utils.DataUtils.CommandUtils;
import IO.IOInterfaceStream;

import java.io.Console;
import java.io.IOException;

public class UserUtils {
    private String login;
    private char[] password;
    public UserUtils(String login, char[] password){
        this.login=login;
        this.password=password;
    }
    public static UserUtils createUser(IOInterfaceStream ioUser, IOInterfaceStream ioServer) throws IOException {
        boolean permission=false;
        String login="";
        char[]password=null;
        Console console=System.console();
        while(!permission){
            ioUser.writeln("Необходимо войти в свое учетную запись, иначе вы не сможете выполнять команды с данными.\n" +
                    " Введите sign_in для входа.\n" +
                    " Для регистрации введите check_in. \n" +
                    "Если вы хотите завершить работу введите exit");
            String request=ioUser.readLine();
            if (request.equals("check_in")){
                ioUser.writeln("Введите имя пользователя :");
                login=ioUser.readLine();
                while(login.isEmpty()){
                    if (login.isEmpty()) ioUser.writeln("Имя пользователя не может быть пустым");
                    ioUser.writeln("Введите имя пользователя :");
                    login=ioUser.readLine();
                }
                ioUser.writeln("Введите пароль :");
                password=ioUser.readLine().toCharArray();
                CommandUtils commandUtils =new CommandUtils("check_in",null);
                commandUtils.setLogin(login);
                commandUtils.setPassword(password);
                ioServer.writeObj(commandUtils);
            }
            else if (request.equals("sign_in")){
                ioUser.writeln("Введите имя пользователя :");
                login=ioUser.readLine();
                ioUser.writeln("Введите пароль :");
                password=ioUser.readLine().toCharArray();
                CommandUtils commandUtils =new CommandUtils("sign_in",null);
                commandUtils.setLogin(login);
                commandUtils.setPassword(password);
                ioServer.writeObj(commandUtils);
            }
            else if (request.equals("exit")){
                ioUser.writeln("Завершение работы");
                System.exit(0);
            }
            else{
                ioUser.writeln("Неверный ввод команды. Повторите ввод");
                continue;
            }
            while (!ioServer.ready()){

            }
            StringBuilder sb=new StringBuilder();
            while (ioServer.ready()){
                sb.append(ioServer.readLine()).append("\n");
            }
            if (sb.toString().equals("Вход произошел успешно\n")){
                permission=true;
            }
            ioUser.writeln(sb.toString());
        }
        return new UserUtils(login,password);
    }

    public char[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
