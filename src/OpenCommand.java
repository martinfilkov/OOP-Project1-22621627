import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand  implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        // Checks if file is submitted
        if (args.size() < 1) {
            System.out.println("Error: No file path provided");
            return;
        }

        String filepath = args.get(0);

        // Checks if the file in the path is of json format
        if (!filepath.endsWith(".json")) {
            System.out.println("Error: Only JSON files are supported");
            return;
        }

        // Extracts the path and reads all its content
        // FileManager is used to store the information without saving it in an external file
        Path path = Paths.get(filepath);
        if(Files.exists(path)){
            FileManager.getInstance().setPath(path);
            FileManager.getInstance().setContent(new String(Files.readAllBytes(path)));
            System.out.printf("Successfully opened %s\n", filepath);
        }
        else {
            // If the file does not exist a basic valid json file (content= "{}") is created at this path.
            try {
                Files.createDirectories(path.getParent());
                Files.write(path, "{}".getBytes());
                FileManager.getInstance().setPath(path);
                FileManager.getInstance().setContent("{}");
                System.out.printf("File not found. A new JSON file has been created at %s\n", filepath);
            } catch (IOException e) {
                System.out.println("Error creating the file: " + e.getMessage());
            }
        }
    }
}
