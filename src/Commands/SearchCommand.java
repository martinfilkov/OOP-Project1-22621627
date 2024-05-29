package Commands;

import Interfaces.Command;
import JsonStructure.JsonObject;
import Manager.FileManager;

import java.util.List;

/**
 * This class implements the search command which allows the user to search for a specific key within the content of the currently opened file.
 * It checks that the file is opened, validated, and that a search key is provided before performing the search.
 */
public class SearchCommand implements Command {
    /**
     * Executes the search command. This method searches for a specific key in the currently opened and validated file's content.
     * It requires that a key is provided and that the file is opened and validated.
     *
     * @param args the arguments provided to the command; expects the search key as the first argument.
     */
    @Override
    public void execute(List<String> args) {
        if (args.isEmpty()) {
            System.out.println("Error: No search key provided");
            return;
        }

        FileManager fileManager = FileManager.getInstance();

        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened");
            return;
        }

        if (!fileManager.isValid()) {
            System.out.println("Error: File must be validated to perform a search");
            return;
        }

        JsonObject content = fileManager.getContent();
        String searchKey = args.get(0);

        if (!content.containsKey(searchKey)) {
            System.out.println("Error: Key not found");
            return;
        }

        Object value = content.get(searchKey);
        System.out.println(searchKey + ": " + value);
    }
}
