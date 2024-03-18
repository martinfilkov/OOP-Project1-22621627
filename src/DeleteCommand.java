import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DeleteCommand implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened to save");
            return;
        }

        if (args.isEmpty()) {
            System.out.println("Error: No path provided");
            return;
        }

        Path deletePath = Paths.get(args.get(0)).toAbsolutePath();

        if(Files.exists(deletePath)){
            Files.delete(deletePath);
            System.out.printf("Deleted file: %s", deletePath);
        }
        else {
            System.out.println("Error: The path provided does not exist");
        }
    }
}
