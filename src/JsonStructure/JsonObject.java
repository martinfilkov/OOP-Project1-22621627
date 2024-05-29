package JsonStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a JSON object.
 * Provides methods to put, get, remove, and check for keys in the JSON object.
 * Supports converting the JSON object to a JSON-formatted string.
 */
public class JsonObject {
    private final Map<String, Object> map;

    /**
     * Constructs a new, empty JsonObject.
     */
    public JsonObject() {
        map = new HashMap<>();
    }

    /**
     * Adds a key-value pair to the JSON object.
     *
     * @param key   the key to be added
     * @param value the value to be associated with the key
     */
    public void put(String key, Object value) {
        map.put(key, value);
    }

    /**
     * Retrieves the value associated with a specific key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or {@code null} if the key is not found
     */
    public Object get(String key) {
        return map.get(key);
    }

    /**
     * Removes the key-value pair associated with the specified key from the JSON object.
     *
     * @param keyToDelete the key whose key-value pair is to be removed
     */
    public void remove(String keyToDelete) {
        map.remove(keyToDelete);
    }

    /**
     * Checks if the JSON object contains the specified key.
     *
     * @param key the key whose presence in the JSON object is to be tested
     * @return {@code true} if the JSON object contains the specified key, {@code false} otherwise
     */
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /**
     * Returns a string representation of the JSON object in JSON format.
     *
     * @return a JSON-formatted string representing the JSON object
     */
    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        int size = map.size();
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonBuilder.append("\"").append(entry.getKey()).append("\":");
            jsonBuilder.append(entry.getValue());
            if (i < size - 1) {
                jsonBuilder.append(",");
            }
            i++;
        }
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
