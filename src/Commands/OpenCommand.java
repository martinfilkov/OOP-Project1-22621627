package Commands;

import Interfaces.Command;
import JsonStructure.JsonObject;
import Manager.FileManager;
import JsonStructure.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * This class represents a command to open a specified file.
 * It supports handling of JSON files, ensuring that only these files are opened.
 */
public class OpenCommand  implements Command {
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
        try {
            FileManager fileManager = FileManager.getInstance();

            if (Files.notExists(path)) {
                Files.writeString(path, "{}", StandardOpenOption.CREATE_NEW);
                System.out.println("File does not exist. Created new file with basic JSON structure.");
            }

            String content = Files.readString(path);
            JsonObject jsonObject = JsonParser.parseJson(content);
            fileManager.setPath(path);
            fileManager.setContent(jsonObject);
            fileManager.setValid(false);
            System.out.println("File opened successfully.");
        } catch (IOException e) {
            System.out.println("Error: Unable to read or create file.");
        } catch (Exception e) {
            System.out.println("Error: Invalid JSON content.");
        }
    }
}
