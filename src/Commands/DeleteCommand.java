package Commands;

import Interfaces.Command;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class implements the delete command which is responsible for deleting a specified JSON file.
 * It checks that the provided file path points to a JSON file before attempting deletion.
 */
public class DeleteCommand implements Command {
    /**
     * Executes the delete command. This method deletes the specified file if it is a JSON file.
     * An error is thrown if the file is not found, not a JSON file, or if there are access restrictions.
     *
     * @param args the arguments provided to the command; expects the file path as the first argument.
     * @throws IOException if an IO error occurs during file deletion.
     */
    @Override
    public void execute(List<String> args) throws IOException {
        if (args.isEmpty()) {
            System.out.println("Error: No path provided");
            return;
        }

        Path deletePath = Paths.get(args.get(0)).toAbsolutePath();

        try {
            if (Files.exists(deletePath)) {
                if (!deletePath.toString().endsWith(".json")) {
                    System.out.println("Error: Only JSON files are supported. You must include the filename in the path!");
                    return;
                }

                Files.delete(deletePath);
                System.out.printf("Deleted file: %s", deletePath);
            } else {
                System.out.println("Error: File does not exist");
            }
        } catch (AccessDeniedException e) {
            System.out.printf("Error: An error occurred while deleting the file: %s\n", e.getMessage());
        }
    }
}
