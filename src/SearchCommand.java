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

        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened");
        }

        if(!FileManager.getInstance().isValid()){
            System.out.println("Error: File must be validated to perform a search");
            return;
        }

        String content = FileManager.getInstance().getContent();
        String searchKey = "\"" + args.get(0) + "\"";

        if (content == null || content.isEmpty()) {
            System.out.println("Error: No file loaded or file is empty");
            return;
        }

        int keyIndex = content.indexOf(searchKey);
        if (keyIndex == -1) {
            System.out.println("Error: Key not found");
            return;
        }

        int valueStart = content.indexOf(":", keyIndex) + 1;
        int valueEnd;
        String value;

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
            valueEnd = valueEnd != -1 ? valueEnd : content.indexOf("}", valueStart);
        }

        value = content.substring(valueStart, valueEnd).trim();

        System.out.println(searchKey + ": " + value);
    }
}
