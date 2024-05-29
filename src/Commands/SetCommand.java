package Commands;

import Interfaces.Command;
import JsonStructure.JsonObject;
import Manager.FileManager;

import java.util.List;

/**
 * This class implements the set command which allows the user to modify a specific key's value within the content of the currently opened file.
 * It checks that the file is opened, validated, and that the appropriate number of arguments are provided before performing the modification.
 */
public class SetCommand implements Command {
    /**
     * Executes the set command. This method modifies the value associated with a specified key in the currently opened and validated file's content.
     * It requires that the file is opened, validated, and that both a key and value are provided.
     *
     * @param args the arguments provided to the command; expects the key as the first argument and the new value as the subsequent arguments.
     */
    @Override
    public void execute(List<String> args){
        FileManager fileManager = FileManager.getInstance();

        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened");
            return;
        }

        if (!fileManager.isValid()) {
            System.out.println("Error: File is not validated");
            return;
        }

        if (args.size() < 2) {
            System.out.println("Error: Not enough arguments provided. Usage: set <key> <new value>");
            return;
        }

        String key = args.get(0);
        String value = String.join(" ", args.subList(1, args.size()));

        String newValue = FileManager.parseValue(value);

        JsonObject content = fileManager.getContent();

        if (!content.containsKey(key)) {
            System.out.println("Error: Key not found");
            return;
        }

        if (newValue == null) {
            System.out.println("Error: Invalid input format for value. Ensure proper quoting for strings or correct JSON formatting.");
            return;
        }

        content.put(key, newValue);
        fileManager.setContent(content);
        System.out.println("Key " + key + " updated successfully.");
    }

    private int findValueEnd(String content, int start) {
        while (Character.isWhitespace(content.charAt(start))) {
            start++;
        }

        char firstChar = content.charAt(start);
        if (firstChar == '[' || firstChar == '{') {
            return content.indexOf(firstChar == '[' ? ']' : '}', start) + 1;
        }
        else if (firstChar == '\"') {
            return content.indexOf("\"", start + 1) + 1;
        } else {
            int end = content.indexOf(",", start);
            return end == -1 ? content.indexOf("}", start) : end;
        }
    }
}
