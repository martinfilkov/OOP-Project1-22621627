import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteCommand implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        // Checks if a path is provided
        if (args.isEmpty()) {
            System.out.println("Error: No path provided");
            return;
        }

        Path deletePath = Paths.get(args.get(0)).toAbsolutePath();

        // Checks if such file exists
        // If the file exists checks if it ends with json because those are the supported files by the app
        // If all is good the file is deleted
        try {
            if (Files.exists(deletePath)) {
                if (!deletePath.toString().endsWith(".json")) {
                    System.out.println("Error: Only JSON files are supported. You must include the filename in the path!");
                    return;
                }

                Files.delete(deletePath);
                System.out.printf("Deleted file: %s", deletePath);
            } else {
                System.out.println("Error: The path provided does not exist");
            }
        } catch (AccessDeniedException e) {
            // THE PROVIDED PATH MUST INCLUDE THE NAME OF THE JSON FILE WE WANT TO DELETE;
            System.out.println("Error: Access denied. Cannot save the file to the specified location.");
        } catch (IOException e) {
            System.out.printf("Error: An error occurred while deleting the file: %s\n", e.getMessage());
        }
    }
}
