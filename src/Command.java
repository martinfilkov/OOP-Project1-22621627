import java.io.IOException;
import java.util.List;

/**
 * Interface defining the structure of commands in the application.
 * Each command must implement this interface, providing its specific execution logic.
 */
public interface Command {
    /**
     * Executes the command with the given arguments.
     * Implementations of this method should handle the command logic based on the arguments provided.
     *
     * @param args the list of arguments necessary for executing the command.
     * @throws IOException if an IO error occurs during command execution.
     */
    void execute(List<String> args) throws IOException;
}
