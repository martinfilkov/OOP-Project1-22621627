package Commands;

import Interfaces.Command;
import Manager.FileManager;

import java.io.IOException;
import java.nio.file.Files;
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

        if(fileManager.getPath() == null){
            System.out.println("Error: No file opened");
            return;
        }

        if(!fileManager.isValid()){
            System.out.println("Error: File must be validated to perform a deletion");
            return;
        }

        String content = fileManager.getContent();
        String keyToDelete = "\"" + args.get(0) + "\"";

        if (content == null || content.isEmpty()) {
            System.out.println("Error: No file loaded or file is empty");
            return;
        }

        int keyIndex = content.indexOf(keyToDelete);
        if (keyIndex == -1) {
            System.out.println("Error: Key not found");
            return;
        }

        int valueStart = content.indexOf(":", keyIndex) + 1;
        int valueEnd;
        while (Character.isWhitespace(content.charAt(valueStart))) {
            valueStart++;
        }

        if (content.charAt(valueStart) == '[') {
            valueEnd = content.indexOf("]", valueStart) + 1;
        }
        else if(content.charAt(valueStart) == '{') {
            valueEnd = content.indexOf("}", valueStart) + 1;
        }
        else {
            valueEnd = content.indexOf(",", valueStart);
            if (valueEnd == -1) {
                valueEnd = content.indexOf("}", valueStart);
            }
        }

        String keyValuePair;
        if (valueEnd == -1) {
            keyValuePair = content.substring(keyIndex);
        } else {
            keyValuePair = content.substring(keyIndex, valueEnd + 1);
        }

        if (keyIndex > 0 && content.charAt(keyIndex - 1) == ',') {
            keyValuePair = "," + keyValuePair;
        } else if (valueEnd != -1 && content.charAt(valueEnd + 1) == ',') {
            keyValuePair = keyValuePair + ",";
        }

        String updatedContent = content.replace(keyValuePair, "").trim();

        fileManager.setContent(updatedContent);
        try {
            Files.writeString(fileManager.getPath(), updatedContent);
            System.out.printf("Deleted key '%s' from the JSON file.\n", args.get(0));
        } catch (IOException e) {
            System.out.println("Error: Failed to save updated content to the file.");
        }
    }
}
