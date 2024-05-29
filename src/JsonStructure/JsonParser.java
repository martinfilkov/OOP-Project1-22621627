package JsonStructure;

import static Manager.FileManager.parseValue;

/**
 * A utility class for parsing JSON strings into JsonObject instances.
 */
public class JsonParser {
    /**
     * Parses a JSON string and returns a JsonObject.
     *
     * @param input the JSON string to parse.
     * @return the parsed JsonObject.
     */
    public static JsonObject parseJson(String input) {
        JsonObject jsonObject = new JsonObject();
        input = input.trim();

        if (input.startsWith("{") && input.endsWith("}")) {
            input = input.substring(1, input.length() - 1).trim();
            int braceLevel = 0;
            int bracketLevel = 0;
            StringBuilder key = new StringBuilder();
            StringBuilder value = new StringBuilder();
            String currentKey = null;
            boolean inQuotes = false;
            boolean isParsingKey = true;

            for (char c : input.toCharArray()) {
                if (c == '\"') {
                    inQuotes = !inQuotes;
                } else if (!inQuotes) {
                    if (c == '{') {
                        braceLevel++;
                    } else if (c == '}') {
                        braceLevel--;
                    } else if (c == '[') {
                        bracketLevel++;
                    } else if (c == ']') {
                        bracketLevel--;
                    } else if (c == ':' && braceLevel == 0 && bracketLevel == 0) {
                        currentKey = key.toString().trim().replaceAll("^\"|\"$", "");
                        key.setLength(0);
                        isParsingKey = false;
                        continue;
                    } else if (c == ',' && braceLevel == 0 && bracketLevel == 0) {
                        jsonObject.put(currentKey, parseValue(value.toString().trim()));
                        value.setLength(0);
                        isParsingKey = true;
                        continue;
                    }
                }

                if (isParsingKey) {
                    key.append(c);
                } else {
                    value.append(c);
                }
            }

            if (currentKey != null && value.length() > 0) {
                jsonObject.put(currentKey, parseValue(value.toString().trim()));
            }
        }
        return jsonObject;
    }
}
