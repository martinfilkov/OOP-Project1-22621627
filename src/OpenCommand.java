import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class OpenCommand  implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        if (args.size() < 1) {
            System.out.println("Error: No file path provided!");
            return;
        }

        String filepath = args.get(0);

        if (!filepath.endsWith(".json")) {
            System.out.println("Error: Only JSON files are supported!");
            return;
        }

        Path path = Paths.get(filepath);
        if(Files.exists(path)){
            String content = new String(Files.readAllBytes(path)); //find what to do with this information
            System.out.printf("Successfully opened %s\n", filepath);
            System.out.println(content); //testing only will remove
        }
        else {
            System.out.println("Error: The file path you provided does not exist!");
        }
    }
}
