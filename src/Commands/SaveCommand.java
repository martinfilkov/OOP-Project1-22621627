package Commands;

import Interfaces.Command;
import JsonStructure.JsonObject;
import Manager.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This class implements the save command which saves the current content to the opened file.
 * It checks that a file is opened and that the path is valid before proceeding with the save operation.
 */
public class SaveCommand implements Command {
    /**
     * Executes the save command. This method saves the currently opened file's content to its existing path.
     * It ensures that a file is currently opened and the path is valid before saving.
     *
     * @param args the arguments provided to the command; not used in this command.
     * @throws IOException if an I/O error occurs during file saving.
     */
    @Override
    public void execute(List<String> args) throws IOException {
        FileManager fileManager = FileManager.getInstance();

        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened to save");
            return;
        }

        JsonObject content = fileManager.getContent();
        Path path = fileManager.getPath();

        if (Files.exists(path)) {
            String contentString = content.toString();
            Files.writeString(path, contentString);
            System.out.printf("Successfully saved to %s%n", path);
        } else {
            System.out.println("Error: The file path you provided is invalid");
        }
    }
}
