package cs1302.api;

/**
 * Represents the weather conditions related to wind.
 */
public class Weather {
    private double ws; // Wind speed
    private int wd;    // Wind direction

    /**
     * Retrieves the wind speed.
     *
     * @return the wind speed in meters per second.
     */
    public double getWs() {
        return ws;
    }

    /**
     * Retrieves the wind direction.
     *
     * @return the wind direction in degrees.
     */
    public int getWd() {
        return wd;
    }
}
