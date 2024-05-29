package Commands;
import Interfaces.Command;
import JsonStructure.JsonObject;
import Manager.FileManager;
import java.util.List;

/**
 * This class implements the delete element command which allows the user to delete a specific key within the content of the currently opened file.
 * It checks that the file is opened, validated, and that a key is provided before performing the deletion.
 */
public class DeleteCommand implements Command {
    /**
     * Executes the delete element command. This method deletes a specific key in the currently opened and validated file's content.
     * It requires that a key is provided and that the file is opened and validated.
     *
     * @param args the arguments provided to the command; expects the key to delete as the first argument.
     */
    @Override
    public void execute(List<String> args) {
        if (args.isEmpty()) {
            System.out.println("Error: No key provided");
            return;
        }

        FileManager fileManager = FileManager.getInstance();

        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened");
            return;
        }

        if (!fileManager.isValid()) {
            System.out.println("Error: File must be validated to perform a deletion");
            return;
        }

        JsonObject content = fileManager.getContent();
        String keyToDelete = args.get(0);

        if (!content.containsKey(keyToDelete)) {
            System.out.println("Error: Key not found");
            return;
        }

        content.remove(keyToDelete);
        fileManager.setContent(content);
        System.out.printf("Deleted key '%s' from the JSON file.\n", keyToDelete);
    }
}
