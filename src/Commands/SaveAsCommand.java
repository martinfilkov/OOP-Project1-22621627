package Commands;

import Interfaces.Command;
import Manager.FileManager;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class implements the save as command which allows the user to save the current file's content to a new file.
 * It checks that the file is opened, a new path is provided, and that the path is for a JSON file before saving.
 */
public class SaveAsCommand implements Command {
    /**
     * Executes the save as command. This method saves the currently opened file's content to a new specified path.
     * It ensures the path is valid, points to a JSON file, and that a file is currently opened and valid.
     *
     * @param args the arguments provided to the command; expects the new file path as the first argument.
     * @throws IOException if an I/O error occurs during file saving.
     */
    @Override
    public void execute(List<String> args) throws IOException {
        if (FileManager.getInstance().getPath() == null) {
            System.out.println("Error: No file opened to save");
            return;
        }

        if (args.isEmpty()) {
            System.out.println("Error: No path provided");
            return;
        }

        Path newPath = Paths.get(args.get(0)).toAbsolutePath();
        String content = FileManager.getInstance().getContent();

        try {
            if (!newPath.toString().endsWith(".json")) {
                System.out.println("Error: File path must be of JSON format");
                return;
            }

            if (!Files.exists(newPath.getParent())) {
                Files.createDirectories(newPath.getParent());
            }

            Files.write(newPath, content.getBytes());
            FileManager.getInstance().setPath(newPath);
            System.out.printf("Successfully saved to %s\n", newPath);
        } catch (AccessDeniedException e) {
            System.out.printf("Error: An error occurred while saving the file: %s\n", e.getMessage());
        }
    }
}
