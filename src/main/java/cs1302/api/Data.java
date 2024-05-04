package cs1302.api;

/**
 * Represents the "data" part of an API response, which may include current conditions.
 */
public class Data {
    private Current current; // Current weather conditions object

    /**
     * Retrieves the current weather conditions.
     *
     * @return the current conditions as a {@link Current} object.
     */
    public Current getCurrent() {
        return current;
    }
}
