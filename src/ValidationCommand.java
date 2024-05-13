import java.io.IOException;
import java.util.List;

public class ValidationCommand implements Command{
    @Override
    public void execute(List<String> args) throws IOException {
        String json = FileManager.getInstance().getContent();

        // Checks if content is empty
        if (json == null || json.isEmpty()){
            System.out.println("Error: No file loaded or file is empty.");
            return;
        }

        json = json.trim();

        // Splits the string into characters
        char[] charJSON = json.toCharArray();

        // Counts the open brackets to ensure that both opening and closing brackets are the same count
        int openBrackets = 0;

        // InQuote is used to ignore special characters inside of strings
        boolean isQuoted = false;

        // expectColon is used to expect when a colon is needed (right after a key)
        boolean expectColon = false;

        // inObject is used to validate if we are inside a json object or not
        boolean inObject = false;

        int line = 1;

        for (int i = 0; i < charJSON.length; i++){
            char ch =  charJSON[i];

            if(ch == '\n') line++;

            switch (ch) {
                case '{' -> {
                    // Checks if the character is inside a string
                    // If true, it increments the openBrackets and sets that we are in an object and no colon is yet expected
                    if(!isQuoted){
                        openBrackets++;
                        inObject = true;
                        expectColon = false;
                    }
                }
                case '}' -> {
                    // Checks if the opened brackets are more than one
                    if (openBrackets <= 0) {
                        System.out.printf("Error: Unexpected '%s' at position: %d\n", ch, line);
                        return;
                    }
                    // Checks if the character is inside a string
                    if(!isQuoted){
                        // Similarly to the open brackets but decreases the openBrackets count
                        openBrackets--;
                        inObject = true;
                        expectColon = false;
                    }
                }
                case '"' ->  isQuoted = !isQuoted;
                case ':' -> {
                    // Checks if a colon is expected
                    // If expected it reverses expectColon back to false
                    if(expectColon && !isQuoted) expectColon = false;
                    else if(inObject && !isQuoted) {
                        System.out.printf("Error: Unexpected '%s' at position: %d\n", ch, line);
                        return;
                    }
                }
                case ',' -> {
                    //Checks if we are inside an object and not in a string
                    if (inObject && !isQuoted) expectColon = false;
                }
            }

            // Used to decide whether a colon is needed
            // A colon must be present after a key value inside an object
            if (!isQuoted && inObject && ch == '"' && !expectColon) {
                expectColon = true;
            }
        }

        // Multiple checks for closed quotes, brackets and correctly placed colons
        if (isQuoted) {
            System.out.println("Error: Unclosed quote.");
        } else if (openBrackets != 0) {
            System.out.println("Error: Mismatched brackets or braces.");
        } else if (expectColon) {
            System.out.println("Error: Missing ':' after a key in an object.");
        } else {
            System.out.println("Basic JSON structure appears valid.");
            FileManager.getInstance().setValid(true);
        }
    }
}
