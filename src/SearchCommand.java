import java.util.List;

public class SearchCommand implements Command {
    @Override
    public void execute(List<String> args) {
        //Check if there are any arguments
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

        //Extract the content of the file
        //Assign the searched key with brackets around it (keys are in brackets in json)
        String content = FileManager.getInstance().getContent();
        String searchKey = "\"" + args.get(0) + "\"";

        //Check if a file is opened and its content is not empty
        if (content == null || content.isEmpty()) {
            System.out.println("Error: No file loaded or file is empty");
            return;
        }

        //Check if key exists
        int keyIndex = content.indexOf(searchKey);
        if (keyIndex == -1) {
            System.out.println("Error: Key not found");
            return;
        }

        //Get start index of the key
        int valueStart = content.indexOf(":", keyIndex) + 1;
        int valueEnd;
        String value;

        //Remove whitespaces in front of the value for the next if
        while (Character.isWhitespace(content.charAt(valueStart))) {
            valueStart++;
        }

        //Checks if the value is an array (because arrays can have multiple ',' inside of them) or an object
        //And if so we need to check for '[]' instead of ','
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

        //Extract the value of the key and trim whitespaces
        value = content.substring(valueStart, valueEnd).trim();

        System.out.println(searchKey + ": " + value);
    }
}
