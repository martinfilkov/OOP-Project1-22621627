import java.util.List;

public class HelpCommand implements Command{
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
