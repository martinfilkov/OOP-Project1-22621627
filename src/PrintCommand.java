import java.util.List;

public class PrintCommand implements Command{
    @Override
    public void execute(List<String> args){
        // The StringBuilder is used to store the formatted string that will be shown to the user
        StringBuilder formatted = new StringBuilder();

        if(FileManager.getInstance().getPath() == null){
            System.out.println("Error: No file opened");
        }

        if(!FileManager.getInstance().isValid()){
            System.out.println("Error: File must be validated to perform a print");
            return;
        }

        // Depth level is used to calculate the tabs used for formatting
        int depthLevel = 0;

        // InQuote is used to ignore special characters inside of strings
        boolean inQuote = false;

        String content = FileManager.getInstance().getContent();

        for (char c : content.toCharArray()) {
            switch (c) {
                case '\"' -> {
                    // Either provides a start of quotes (true) or end of ones (false)
                    inQuote = !inQuote;
                    formatted.append(c);
                }
                case '{', '[' -> {
                    // Checks if  the character is inside a string
                    // If not it increases the depth level
                    // Otherwise just append the character inside the StringBuilder
                    if (!inQuote) {
                        depthLevel++;
                        formatted.append(c).append("\n").append(getDepth(depthLevel));
                    } else {
                        formatted.append(c);
                    }
                }
                case '}', ']' -> {
                    // Same checks as the open brackets but decreases the depth level
                    if (!inQuote) {
                        depthLevel--;
                        formatted.append("\n").append(getDepth(depthLevel)).append(c);
                    } else {
                        formatted.append(c);
                    }
                }
                case ',' -> {
                    // Formats on a new line after a comma
                    if (!inQuote) {
                        formatted.append(c).append("\n").append(getDepth(depthLevel));
                    } else {
                        formatted.append(c);
                    }
                }
                case ':' -> {
                    // Separates key from value for better readability
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

    // Used for adding tabs according to the depth level
    private static String getDepth(int level) {
        return "    ".repeat(Math.max(0, level));
    }
}
