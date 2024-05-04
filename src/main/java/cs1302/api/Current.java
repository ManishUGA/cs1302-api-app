package cs1302.api;

/**
 * Represents the current weather conditions part of an API response.
 */
public class Current {
    private Weather weather; // Weather object containing current weather details

    /**
     * Retrieves the current weather conditions.
     *
     * @return the current weather conditions as a {@link Weather} object.
     */
    public Weather getWeather() {
        return weather;
    }
}
