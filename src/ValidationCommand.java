import java.io.IOException;
import java.util.List;

/**
 * This class implements the validation command which checks the structural integrity of the content of the currently opened file.
 * It ensures that the JSON content has matching brackets and proper string encapsulation before considering it valid.
 */
public class ValidationCommand implements Command{
    /**
     * Executes the validation command. This method checks for the structural validity of the JSON content in the currently opened file.
     * It counts the opening and closing brackets and checks for proper placement of quotes and colons to ensure JSON validity.
     *
     * @param args the arguments provided to the command; not used in this command.
     * @throws IOException if an IO error occurs during content validation.
     */
    @Override
    public void execute(List<String> args) throws IOException {
        String json = FileManager.getInstance().getContent();

        if (json == null || json.isEmpty()){
            System.out.println("Error: No file loaded or file is empty.");
            return;
        }

        json = json.trim();

        char[] charJSON = json.toCharArray();
        int openBrackets = 0;
        boolean isQuoted = false;
        boolean expectColon = false;
        boolean inObject = false;

        int line = 1;

        for (int i = 0; i < charJSON.length; i++){
            char ch =  charJSON[i];

            if(ch == '\n') line++;

            switch (ch) {
                case '{' -> {
                    if(!isQuoted){
                        openBrackets++;
                        inObject = true;
                        expectColon = false;
                    }
                }
                case '}' -> {
                    if (openBrackets <= 0) {
                        System.out.printf("Error: Unexpected '%s' at position: %d\n", ch, line);
                        return;
                    }
                    if(!isQuoted){
                        openBrackets--;
                        inObject = true;
                        expectColon = false;
                    }
                }
                case '"' ->  isQuoted = !isQuoted;
                case ':' -> {
                    if(expectColon && !isQuoted) expectColon = false;
                    else if(inObject && !isQuoted) {
                        System.out.printf("Error: Unexpected '%s' at position: %d\n", ch, line);
                        return;
                    }
                }
                case ',' -> {
                    if (inObject && !isQuoted) expectColon = false;
                }
            }

            if (!isQuoted && inObject && ch == '"' && !expectColon) {
                expectColon = true;
            }
        }

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
