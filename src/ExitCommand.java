import java.util.List;

public class ExitCommand implements Command{
    // Exits the application
    @Override
    public void execute(List<String> args) {
        System.out.println("Exiting application...");
        System.exit(0);
    }
}
