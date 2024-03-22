import java.util.List;

public class CloseCommand implements Command{
    @Override
    public void execute(List<String> args){
        // If closed, all information stored in memory is emptied;
        if (FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened to close");
            return;
        }
        FileManager.getInstance().setContent("");
        FileManager.getInstance().setPath(null);
        System.out.println("Successfully closed file");
    }
}
