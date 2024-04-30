import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandFactory {

    // Used for storing all the commands that are present in the application.
    private static final Map<String, Command> commandMap = new HashMap<>();

    // At the start of the application all available commands are inputted in the HashMap as keys
    // Their implementation class is linked as value to that key
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
    }

    // Tries to match the inputted by the user command with one in the HashMap
    public static void executeCommand(String input) throws IOException {
        // The regex "\\s" clears all whitespaces inside the string so that no blank arguments are passed to the execute command
        List<String> commandParts = List.of(input.split("\\s"));
        String commandName = commandParts.get(0);

        // Create a list of arguments that include all the information except the first inputted word
        // If no words follow the command name a blank arguments List is created
        List<String> args = commandParts.size() > 1 ? new ArrayList<>(commandParts.subList(1, commandParts.size())) : new ArrayList<>();

        // Tries to match a command with one in the HashMap
        Command command = commandMap.get(commandName);

        // If successful, the command is executed
        if (command != null) {
            command.execute(args);
        }
        else {
            System.out.printf("Error: Unknown command \"%s\"", commandName);
        }
    }
}
