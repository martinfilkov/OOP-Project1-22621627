import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandFactory {
    private static final Map<String, Command> commandMap = new HashMap<>();

    static {
        commandMap.put("open", new OpenCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("help", new HelpCommand());
        commandMap.put("save", new SaveCommand());
        commandMap.put("saveas", new SaveAsCommand());
        commandMap.put("close", new CloseCommand());
        commandMap.put("print", new PrintCommand());
        commandMap.put("delete", new DeleteCommand());
    }

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
