package cs1302.api;

/**
 * Represents the API response that includes encapsulated data regarding weather, pollution, etc.
 */
public class ApiResponse {
    private Data data; // Data object containing the response details

    /**
     * Retrieves the data contained in the API response.
     *
     * @return the data as a {@link Data} object.
     */
    public Data getData() {
        return data;
    }
}
