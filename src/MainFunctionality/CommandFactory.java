package MainFunctionality;

import Commands.*;
import Interfaces.Command;
import Interfaces.CommandType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This abstract class serves as a factory for creating command objects based on command names.
 * It maintains a mapping of command names to their corresponding Command objects.
 */
public abstract class CommandFactory {

    /**
     * A map storing all the command names along with their respective command object instances.
     */
    private static final Map<CommandType, Command> commandMap = new HashMap<>();

    static {
        commandMap.put(CommandType.OPEN, new OpenCommand());
        commandMap.put(CommandType.EXIT, new ExitCommand());
        commandMap.put(CommandType.HELP, new HelpCommand());
        commandMap.put(CommandType.SAVE, new SaveCommand());
        commandMap.put(CommandType.SAVEAS, new SaveAsCommand());
        commandMap.put(CommandType.CLOSE, new CloseCommand());
        commandMap.put(CommandType.VALIDATE, new ValidationCommand());
        commandMap.put(CommandType.PRINT, new PrintCommand());
        commandMap.put(CommandType.DELETE, new DeleteCommand());
        commandMap.put(CommandType.SEARCH, new SearchCommand());
        commandMap.put(CommandType.SET, new SetCommand());
        commandMap.put(CommandType.MOVE, new MoveCommand());
        commandMap.put(CommandType.CREATE, new CreateCommand());
    }

    /**
     * Executes a command based on a given string input. This method parses the input to identify
     * the command and any associated arguments. It then attempts to execute the command if it is found
     * in the command map.
     *
     * @param input the complete command line input provided by the user.
     * @throws IOException if an I/O error occurs during command execution.
     */
    public static void executeCommand(String input) throws IOException {
        List<String> commandParts = List.of(input.split("\\s"));
        String commandName = commandParts.get(0);

        List<String> args = commandParts.size() > 1 ? new ArrayList<>(commandParts.subList(1, commandParts.size())) : new ArrayList<>();

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.printf("Error: Unknown command \"%s\"%n", commandName);
            return;
        }

        Command command = commandMap.get(commandType);
        command.execute(args);
    }
}
