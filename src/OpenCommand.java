import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand  implements Command{
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
