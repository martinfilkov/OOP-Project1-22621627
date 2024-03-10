import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class CommandFactory {
    private static final Map<String, Function<List<String>, Command>> commandMap = new HashMap<>();

    static {
        commandMap.put("open", args -> new OpenCommand());
        commandMap.put("exit", args -> new ExitCommand());
    }

    public static void executeCommand(String input) throws IOException {
        List<String> commandParts = List.of(input.split("\\s"));
        String commandName = commandParts.get(0);
        List<String> args = commandParts.size() > 1 ? new ArrayList<>(commandParts.subList(1, commandParts.size())) : new ArrayList<>();


        Function<List<String> ,Command> commandFunction = commandMap.get(commandName);

        if (commandFunction != null) {
            Command command = commandFunction.apply(args);
            command.execute(args);
        }
        else {
            System.out.printf("Error: Unknown command \"%s\"", commandName);
        }
    }
}
