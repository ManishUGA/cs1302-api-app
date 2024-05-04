package cs1302.api;

/**
 * Represents the latitude and longitude of a location.
 */
public class LocationResponse {
    private String lat; // Latitude
    private String lon; // Longitude

    /**
     * Retrieves the latitude of the location.
     *
     * @return the latitude as a string.
     */
    public String getLat() {
        return lat;
    }

    /**
     * Sets the latitude of the location.
     *
     * @param lat the latitude to set.
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * Retrieves the longitude of the location.
     *
     * @return the longitude as a string.
     */
    public String getLon() {
        return lon;
    }

    /**
     * Sets the longitude of the location.
     *
     * @param lon the longitude to set.
     */
    public void setLon(String lon) {
        this.lon = lon;
    }
}
