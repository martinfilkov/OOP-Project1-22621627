package Manager;

import JsonStructure.JsonObject;

import java.nio.file.Path;
import java.util.Scanner;

/**
 * Singleton class for managing file information such as its path and content.
 * Ensures that only one instance of the FileManager exists throughout the application.
 */
public class FileManager {
    private static FileManager instance;
    private Path path;
    private JsonObject content;
    private boolean isValid = false;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private FileManager(){}

    /**
     * Provides the singleton instance of FileManager.
     * If no instance exists, it initializes a new instance.
     *
     * @return the singleton instance of FileManager.
     */
    public static FileManager getInstance(){
        if (instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * Gets the current file path.
     *
     * @return the path of the file.
     */
    public Path getPath() {
        return path;
    }

    /**
     * Sets the file path.
     *
     * @param path the new path of the file.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Gets the content of the file.
     *
     * @return the content of the file as a string.
     */
    public JsonObject getContent() {
        return content;
    }

    /**
     * Sets the content of the file.
     *
     * @param content the new content to set in the file.
     */
    public void setContent(JsonObject content) {
        this.content = content;
    }

    /**
     * Checks if the file is valid.
     *
     * @return true if the file is considered valid, false otherwise.
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Sets the validation state of the file.
     *
     * @param valid the new validation state of the file.
     */
    public void setValid(boolean valid) {
        isValid = valid;
    }

    /**
     * Parses the input string to format it correctly based on its type.
     * Supports boolean, integer, double, JSON objects, JSON arrays, and quoted strings.
     *
     * @param input the string to parse.
     * @return the formatted string if recognizable; otherwise, null.
     */
    public static String parseValue(String input) {
        input = input.trim();
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
