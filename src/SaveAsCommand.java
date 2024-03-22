import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SaveAsCommand implements Command{
    @Override
    public void execute(List<String> args){
        // Checks if a file is opened
        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened to save");
            return;
        }

        // Checks if a path is provided
        if (args.isEmpty()) {
            System.out.println("Error: No path provided");
            return;
        }

        Path newPath = Paths.get(args.get(0)).toAbsolutePath();
        String content = FileManager.getInstance().getContent();

        // If the directory provided does not exist, one is created
        // Checks if the file path is of json format
        // Saves the content of the file at the given path
        try {
            if (newPath.getParent() != null) {
                Files.createDirectories(newPath.getParent());
            }

            if (!newPath.toString().endsWith(".json")) {
                System.out.println("Error: Only JSON files are supported. You must include the filename in the path!");
                return;
            }

            Files.write(newPath, content.getBytes());
            FileManager.getInstance().setPath(newPath);
            System.out.printf("Successfully saved to %s\n", newPath);
        } catch (AccessDeniedException e) {
            // THE PROVIDED PATH MUST INCLUDE THE NAME OF THE JSON FILE WE WANT TO SAVE;
            System.out.println("Error: Access denied. Cannot save the file to the specified location.");
        } catch (IOException e) {
            System.out.printf("Error: An error occurred while saving the file: %s\n", e.getMessage());
        }
    }
}
