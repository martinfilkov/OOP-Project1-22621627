import java.nio.file.Path;
import java.util.Scanner;

// Uses the singleton architecture to create a class with a single instance
// The instance stores information about the file such as its path and content
public class FileManager {
    private static FileManager instance;
    private Path path;
    private String content;
    private boolean isValid = false;
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

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    // Helper method to parse and format the new value correctly based on its type
    public static String parseValue(String input) {
        input = input.trim();

        //I am using scanner because I couldn't find a parse function without throwing an exception
        Scanner in = new Scanner(input);

        if (in.hasNextBoolean()) {
            return String.valueOf(in.nextBoolean());
        } else if (in.hasNextInt()) {
            return String.valueOf(in.nextInt());
        } else if (in.hasNextDouble()) {
            return String.valueOf(in.nextDouble());
        } else if (input.matches("\\[.*]") || input.matches("\\{.*}")) {
            return input;
        } else if (input.startsWith("\"") && input.endsWith("\"") && countMatches(input, '"') == 2) {
            return input;
        }
        in.close();
        return null;
    }

    private static int countMatches(String text, char character) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == character) {
                count++;
            }
        }
        return count;
    }
}
