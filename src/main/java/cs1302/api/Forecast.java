package cs1302.api;

/**
 * Represents the forecast details for wind conditions.
 */
public class Forecast {
    private double ws; // Wind speed
    private int wd; // Wind direction

    /**
     * Gets the wind speed.
     * @return the wind speed in meters per second.
     */
    public double getWs() {
        return ws;
    }

    /**
     * Gets the wind direction.
     * @return the wind direction in degrees.
     */
    public int getWd() {
        return wd;
    }

    /**
     * Sets the wind speed.
     * @param ws the wind speed in meters per second to set.
     */
    public void setWs(double ws) {
        this.ws = ws;
    }

    /**
     * Sets the wind direction.
     * @param wd the wind direction in degrees to set.
     */
    public void setWd(int wd) {
        this.wd = wd;
    }
}
