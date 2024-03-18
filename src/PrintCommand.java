import java.util.List;

public class PrintCommand implements Command{
    @Override
    public void execute(List<String> args){
        StringBuilder formatted = new StringBuilder();
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
