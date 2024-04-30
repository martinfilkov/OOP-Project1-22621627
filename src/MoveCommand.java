import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MoveCommand implements Command{
    @Override
    public void execute(List<String> args){
        if (args.size() < 2) {
            System.out.println("Error: You must enter a sender file path and a receiver one like: move <sender> <receiver>");
            return;
        }

        if(!args.get(0).endsWith(".json") && args.get(1).endsWith(".json")){
            System.out.println("Error: Both file paths must be of json format");
        }

        Path sender = Paths.get(args.get(0)).toAbsolutePath();
        Path receiver = Paths.get(args.get(1)).toAbsolutePath();

        if(!Files.exists(sender)){
            System.out.println("Error: Sender path does not exist");
            return;
        }
        try {
            if (receiver.getParent() != null) {
                Files.createDirectories(receiver.getParent());
            }

            Files.move(sender, receiver);
            System.out.println("Successfully copied " + sender.getFileName().toString() + " to " + receiver.getFileName().toString());
        } catch (AccessDeniedException e) {
            // THE PROVIDED PATH MUST INCLUDE THE NAME OF THE JSON FILE WE WANT TO SAVE;
            System.out.println("Error: Access denied. Cannot save the file to the specified location.");
        } catch (IOException e) {
            System.out.printf("Error: An error occurred while saving the file: %s\n", e.getMessage());
        }
    }
}
