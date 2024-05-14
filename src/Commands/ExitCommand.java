package Commands;

import Interfaces.Command;

import java.util.List;

/**
 * This class implements the exit command to terminate the application.
 * When executed, it prints a message and shuts down the application.
 */
public class ExitCommand implements Command {
    /**
     * Executes the exit command. This method terminates the application.
     *
     * @param args the arguments provided to the command; not used in this command.
     */
    @Override
    public void execute(List<String> args) {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
