package Commands;

import Interfaces.Command;
import Manager.FileManager;

import java.util.List;

/**
 * This class implements the print command which outputs the content of the currently opened file.
 * It ensures that the file is opened and validated before proceeding with the print operation.
 */
public class PrintCommand implements Command {
    /**
     * Executes the print command. This method prints the content of the currently opened and validated file.
     * It formats the content for readability, managing depth levels for nested structures and handling quotes.
     *
     * @param args the arguments provided to the command; not used in this command.
     */
    @Override
    public void execute(List<String> args){
        StringBuilder formatted = new StringBuilder();

        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened");
            return;
        }

        if(!FileManager.getInstance().isValid()){
            System.out.println("Error: File must be validated to perform a print");
            return;
        }

        int depthLevel = 0;

        boolean inQuote = false;

        String content = FileManager.getInstance().getContent();

        for (char c : content.toCharArray()) {
            switch (c) {
                case '\"' -> {
                    inQuote = !inQuote;
                    formatted.append(c);
                }
                case '{', '[' -> {
                    if (!inQuote) {
                        depthLevel++;
                        formatted.append(c).append("\n").append(getDepth(depthLevel));
                    } else {
                        formatted.append(c);
                    }
                }
                case '}', ']' -> {
                    if (!inQuote) {
                        depthLevel--;
                        formatted.append("\n").append(getDepth(depthLevel)).append(c);
                    } else {
                        formatted.append(c);
                    }
                }
                case ',' -> {
                    if (!inQuote) {
                        formatted.append(c).append("\n").append(getDepth(depthLevel));
                    } else {
                        formatted.append(c);
                    }
                }
                case ':' -> {
                    if (!inQuote) {
                        formatted.append(c).append(" ");
                    } else {
                        formatted.append(c);
                    }
                }
                default -> {
                    if (!Character.isWhitespace(c) || inQuote) {
                        formatted.append(c);
                    }
                }
            }
        }

        System.out.println(formatted);
    }

    private static String getDepth(int level) {
        return "    ".repeat(Math.max(0, level));
    }
}
