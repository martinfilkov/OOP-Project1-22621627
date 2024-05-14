import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class represents a command to open a specified file.
 * It supports handling of JSON files, ensuring that only these files are opened.
 */
public class OpenCommand  implements Command{
    /**
     * Executes the open command using the provided arguments to locate and open a file.
     * It checks if the file path is provided and validates if the file is a JSON format.
     * If the conditions are met, the file is read and its content is loaded into memory.
     *
     * @param args the arguments provided to the command; expects the file path as the first argument.
     * @throws IOException if there is an issue accessing the file on disk.
     */
    @Override
    public void execute(List<String> args) throws IOException {
        if (args.size() < 1) {
            System.out.println("Error: No file path provided");
            return;
        }

        String filepath = args.get(0);

        if (!filepath.endsWith(".json")) {
            System.out.println("Error: Only JSON files are supported");
            return;
        }

        Path path = Paths.get(filepath);
        if(Files.exists(path)){
            FileManager.getInstance().setPath(path);
            FileManager.getInstance().setContent(new String(Files.readAllBytes(path)));
            System.out.printf("Successfully opened %s\n", filepath);
        }
        else {
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
