import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class SetCommand implements Command {
    @Override
    public void execute(List<String> args) throws IOException {
        FileManager fileManager = FileManager.getInstance();

        //Checking if a file is opened
        if (fileManager.getPath() == null) {
            System.out.println("Error: No file opened");
            return;
        }

        //Checking if the file is validated
        if (!fileManager.isValid()) {
            System.out.println("Error: File is not validated");
            return;
        }

        // Ensure at least two arguments: key and a value
        if (args.size() < 2) {
            System.out.println("Error: Not enough arguments provided. Usage: set <key> <new value>");
            return;
        }

        String key = args.get(0);
        // A string may contain more than one word, so we need to join them
        String value = String.join(" ", args.subList(1, args.size()));

        // Check type of the value
        String newValue = parseValue(value);

        String content = fileManager.getContent();

        //The key should not be quoted when calling the command
        String searchKey = "\"" + key + "\"";


        //Search for key
        int keyIndex = content.indexOf(searchKey);
        if (keyIndex == -1) {
            System.out.println("Error: Key not found");
            return;
        }

        if (newValue == null) {
            System.out.println("Error: Invalid input format for value. Ensure proper quoting for strings or correct JSON formatting.");
            return;
        }

        //Get start and end positions of the key value pair
        int valueStart = content.indexOf(":", keyIndex) + 1;
        int valueEnd = findValueEnd(content, valueStart);

        // Replace the old value with the new value in the content string
        String updatedContent = content.substring(0, valueStart) + newValue + content.substring(valueEnd);
        fileManager.setContent(updatedContent);
        System.out.println("Key " + key + " updated successfully.");
    }

    // Helper method to parse and format the new value correctly based on its type
    private String parseValue(String input) {
        input = input.trim();

        //I am using scanner because I couldn't find a parse function without throwing an exception
        Scanner in = new Scanner(input);

        if (in.hasNextBoolean()) {
            return String.valueOf(in.nextBoolean());
        } else if (in.hasNextInt()) {
            return String.valueOf(in.nextInt());
        } else if (in.hasNextDouble()) {
            return String.valueOf(in.nextDouble());
        } else if (input.matches("\\[.*]") || input.matches("\\{.*}")) {
            return input;
        } else if (input.startsWith("\"") && input.endsWith("\"") && countMatches(input, '"') == 2) {
            return input;
        }
        in.close();
        return null;
    }

    private int countMatches(String text, char character) {
        int count = 0;
        for (char c : text.toCharArray()) {
            if (c == character) {
                count++;
            }
        }
        return count;
    }

    private int findValueEnd(String content, int start) {
        //Skip the whitespaces
        while (Character.isWhitespace(content.charAt(start))) {
            start++;
        }

        char firstChar = content.charAt(start);

        //Specifically check if the value is an object or an array because you would take everything inside, not only the first value
        if (firstChar == '[' || firstChar == '{') {
            return content.indexOf(firstChar == '[' ? ']' : '}', start) + 1;
        }
        //Check for strings
        else if (firstChar == '\"') {
            return content.indexOf("\"", start + 1) + 1;
        } else {
            int end = content.indexOf(",", start);
            return end == -1 ? content.indexOf("}", start) : end;
        }
    }
}
