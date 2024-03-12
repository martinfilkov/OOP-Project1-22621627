import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveCommand implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened to save");
            return;
        }

        String content = FileManager.getInstance().getContent();
        Path path = FileManager.getInstance().getPath();

        if (Files.exists(path)) {
            Files.write(path, content.getBytes());
            System.out.printf("Successfully saved to %s", path);
        }
        else System.out.println("Error: The file path you provided is invalid");
    }
}
