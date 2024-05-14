import java.util.List;

/**
 * This class implements the help command which provides a help menu to the user.
 * It lists all available commands and their descriptions.
 */
public class HelpCommand implements Command{
    /**
     * Executes the help command. This method prints out a list of all available commands
     * along with their descriptions to help the user understand how to use them.
     *
     * @param args the arguments provided to the command; not used in this command.
     */
    @Override
    public void execute(List<String> args){
        System.out.println("The following command options are supported");
        System.out.printf("%-15s %s%n", "open <file>", "opens <file>");
        System.out.printf("%-15s %s%n", "close", "closes currently opened file");
        System.out.printf("%-15s %s%n", "save", "saves the currently open file");
        System.out.printf("%-15s %s%n", "saveas <file>", "saves the currently open file in <file>");
        System.out.printf("%-15s %s%n", "help", "prints this information");
        System.out.printf("%-15s %s%n", "exit", "exits the program");
    }
}
