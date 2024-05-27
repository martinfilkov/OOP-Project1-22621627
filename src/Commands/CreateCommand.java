package Commands;

import Interfaces.Command;
import Manager.FileManager;

import java.util.List;

/**
 * This class implements the create command which is responsible for adding a new key-value pair to an open JSON file.
 * The command expects the file to be opened and validated before adding new data.
 */
public class CreateCommand implements Command {
    /**
     * Executes the create command. It adds a new key-value pair to the currently open file.
     * Requires the file to be both open and validated to proceed with the creation.
     *
     * @param args the arguments provided to the command, expects at least two arguments where
     *             the first argument is the key and the remaining argument(s) form the value.
     */

    @Override
    public void execute(List<String> args){
        FileManager fileManager = FileManager.getInstance();

        if (args.size() < 2) {
            System.out.println("Error: You must enter a file path, key and a value like: create <key> <value>");
            return;
        }

        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened");
            return;
        }

        if (!fileManager.isValid()) {
            System.out.println("Error: File is not validated");
            return;
        }

        String content = fileManager.getContent();
        String newKey = "\"" + args.get(0) + "\"";
        String value = FileManager.parseValue(String.join(" ", args.subList(1, args.size())));

        if (value == null) {
            System.out.println("Error: Invalid input format for value. Ensure proper quoting for strings or correct JSON formatting.");
            return;
        }

        int keyIndex = content.indexOf(newKey);

        if (keyIndex != -1) {
            System.out.println("Error: Key is already created");
            return;
        }

        String keyValue = buildKey(newKey, value);
        String updatedContent = insertKeyValue(content, keyValue);

        fileManager.setContent(updatedContent);
        System.out.println("Successfully added new key: " + newKey);
    }

    private String buildKey(String key, String value){
        return key + ": " + value;
    }

    private String insertKeyValue(String content, String keyValue) {
        int lastIndex = content.lastIndexOf('}');
        if (lastIndex == -1) {
            return "{" + keyValue + "}";
        } else {
            String beforeLastBrace = content.substring(0, lastIndex);
            String afterLastBrace = content.substring(lastIndex);
            if (beforeLastBrace.trim().endsWith("{")) {
                return beforeLastBrace + keyValue + afterLastBrace;
            } else {
                return beforeLastBrace + ", " + keyValue + afterLastBrace;
            }
        }
    }
}
