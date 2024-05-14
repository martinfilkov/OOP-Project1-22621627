package MainFunctionality;

import Commands.*;
import Interfaces.Command;

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
    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put("open", new OpenCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("save", new SaveCommand());
        commandMap.put("saveas", new SaveAsCommand());
        commandMap.put("close", new CloseCommand());
        commandMap.put("validate", new ValidationCommand());
        commandMap.put("print", new PrintCommand());
        commandMap.put("delete", new DeleteCommand());
        commandMap.put("search", new SearchCommand());
        commandMap.put("set", new SetCommand());
        commandMap.put("move", new MoveCommand());
        commandMap.put("create", new CreateCommand());
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

        Command command = commandMap.get(commandName);

        if (command != null) {
            command.execute(args);
        }
        else {
            System.out.printf("Error: Unknown command \"%s\"", commandName);
        }
    }
}
