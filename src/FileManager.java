import java.nio.file.Path;

// Uses the singleton architecture to create a class with a single instance
// The instance stored information about the file such as its path and content
public class FileManager {
    private static FileManager instance;
    private Path path;
    private String content;
    private FileManager(){}

    public static FileManager getInstance(){
        if (instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
