package Server.Launch;

import Utils.DataUtils.CommandUtils;
import Server.Commands.*;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Класс, который обрабатывает ввод команд с консоли
 */
public class Launch implements Callable<String> {
    private CommandUtils commandUtils;
    private ControlUnit controlUnit;

    /**
     * Конструктор создания команд
     */
    public Launch(CityService cityService, CommandUtils commandUtils, ControlUnit controlUnit) throws IOException {
        this.controlUnit = controlUnit;
        this.commandUtils = commandUtils;
        initCommands(cityService, controlUnit);
    }
    private void initCommands(CityService cityService, ControlUnit controlUnit) throws IOException {
        new ExitCommand(controlUnit, cityService);
        new GroupCountingByPopulationCommand(cityService, controlUnit);
        new HelpCommand(controlUnit);
        new SortCommand(controlUnit, cityService);
        new RemoveByIdCommand(controlUnit, cityService);
        new RemoveAllBYMetersAboveSeaLevelCommand(cityService, controlUnit);
        new ClearCommand(controlUnit, cityService);
        new ShowCommand(controlUnit, cityService);
        new HistoryCommand(controlUnit, cityService);
        new RemoveLastCommand(controlUnit, cityService);
        new PrintAscendingCommand(controlUnit, cityService);
        new PrintAscendingCommand(controlUnit, cityService);
        new AddCommand(controlUnit, cityService);
        new UpdateCommand(controlUnit, cityService);
        new SignInCommand(controlUnit);
        new CheckInCommand(controlUnit);
    }

    @Override
    public String call() throws Exception {
        String answer = null;
        try {
            answer = controlUnit.executeCommand(commandUtils.getNameCommand(), commandUtils);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
}


