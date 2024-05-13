import java.util.List;

public class CreateCommand implements Command{
    @Override
    public void execute(List<String> args){
        FileManager fileManager = FileManager.getInstance();

        if (args.size() < 2) {
            System.out.println("Error: You must enter a file path, key and a value like: create <key> <value>");
            return;
        }

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

        String content = fileManager.getContent();
        String newKey = "\"" + args.get(0) + "\"";
        String value = FileManager.parseValue(String.join(" ", args.subList(1, args.size())));

        System.out.println(String.join(" ", args.subList(1, args.size())));

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
