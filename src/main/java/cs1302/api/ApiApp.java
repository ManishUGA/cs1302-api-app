package cs1302.api;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.scene.input.KeyEvent;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.Node;
import cs1302.api.LocationResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javafx.scene.text.Font;



/**
 * REPLACE WITH NON-SHOUTING DESCRIPTION OF YOUR APP.
 */
public class ApiApp extends Application {


    private TextField nameInput = new TextField();
    private Label coordinatesLabel = new Label("Coordinates will appear here");
    private VBox root = new VBox(10);
    private Scene scene;
    private String latitude;
    private String longitude;
    private ComboBox<String> locationSelector;
    private List<Forecast> forecasts;

    private static final String IQAIR_API_KEY = "7efd9977-ea62-4845-8581-bf14a6f6a62e";
    private static final String LOCATION_IQ_API_KEY = "pk.9e9dedacb2acce21c1dd1bcf779c542e";

    /**
     * Constructs an {@code ApiApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public ApiApp() {
        root = new VBox();
    } // ApiApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        setupScene1();
        stage.setTitle("AirDetails Application");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Sets up the primary scene for the application. This method initializes
     * and configures the main GUI components such as labels, text fields,
     * buttons, and combo boxes. It also sets up handlers for the buttons
     * to initiate fetching coordinates and air quality data based on user input.
     */

    private void setupScene1() {
        root.setAlignment(Pos.TOP_CENTER);
        root.setPrefSize(1280, 720);

        scene = new Scene(root, 1280, 720);

        Label titleLabel = new Label("Location and Air Data Fetcher");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        nameInput.setPromptText("Enter your name");
        nameInput.setMaxWidth(300);

        locationSelector = new ComboBox<>();
        locationSelector.getItems().addAll("Athens-Clarke, Georgia", "Atlanta, Georgia",
            "Cumming, Georgia", "Miami-Dade, Florida",
                "Charleston County, South Carolina", "Cherokee County, Georgia");
        locationSelector.setPromptText("Select your location");

        Button fetchCoordinatesBtn = new Button("Get Coordinates");
        fetchCoordinatesBtn.setOnAction(e -> fetchCoordinates());

        Button submitButton = new Button("Get Air Speed and Direction Data");
        submitButton.setOnAction(event -> {
            if (!latitude.isEmpty() && !longitude.isEmpty()) {
                fetchAirQualityData(latitude, longitude);
            } else {
                showAlert("Location not set. Please fetch coordinates first.");
            }
        });

        root.getChildren().addAll(titleLabel, nameInput, locationSelector,
            fetchCoordinatesBtn, coordinatesLabel, submitButton);
    }

    /**
     * Fetches the geographic coordinates based on the selected location from the combo box.
     * This method encodes the selected location to make it suitable for URL usage and constructs
     * a request URL to query the LocationIQ API. It sends an asynchronous HTTP request to retrieve
     * the coordinates. If successful, it passes the response to a handler; otherwise, it updates
     * the UI to prompt the user to select a location.
     */
    private void fetchCoordinates() {
        String location = locationSelector.getValue();
        if (location != null) {
            String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
            String requestUrl = String.format("https://us1.locationiq.com/v1/search.php?key=%s&q=%s&format=json",
                LOCATION_IQ_API_KEY, encodedLocation);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::processLocationResponse)
                .join();
        } else {
            coordinatesLabel.setText("Please select a location.");
        }
    }



    /**
     * Processes the JSON response from the LocationIQ API for geographic coordinates.
     * This method parses the JSON response into LocationResponse objects. If the response
     * contains valid location data, it updates the UI with the latitude and longitude.
     * If no location data is found, it sets the coordinates label to indicate that no
     * coordinates were found. This method assumes the first response is the correct one
     * if multiple responses are received.
     *
     * @param jsonResponse The JSON string response from the LocationIQ API.
     */

    private void processLocationResponse(String jsonResponse) {
        LocationResponse[] responses = new Gson().fromJson(jsonResponse, LocationResponse[].class);
        if (responses.length > 0) {
            LocationResponse response = responses[0]; // Assuming the first response is what we

            Platform.runLater(() -> {
                latitude = response.getLat();
                longitude = response.getLon();
                coordinatesLabel.setText("Latitude: " + response.getLat() +
                    ", Longitude: " + response.getLon());
            });
        } else {
            coordinatesLabel.setText("No coordinates found.");
        }
    }


    /**
     * Displays an alert dialog with an error message. This method creates a modal alert
     * dialog that shows the specified message and requires user interaction to dismiss.
     * It is used to inform the user of errors or important notices that require acknowledgment.
     *
     * @param message The message to be displayed in the alert dialog.
     */

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Fetches air quality data for the specified latitude and longitude from the AirVisual API.
     * Constructs a URL using the coordinates and API key, sends an asynchronous HTTP request,
     * and processes the response asynchronously when received.
     *
     * @param lat the latitude of the location for which air quality data is requested.
     * @param lon the longitude of the location for which air quality data is requested.
     */
    private void fetchAirQualityData(String lat, String lon) {
        String url = String.format("http://api.airvisual.com/v2/nearest_city?lat=%s&lon=%s&key=%s",
            lat, lon, IQAIR_API_KEY);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).
            thenApply(HttpResponse::body).thenAccept(this::processAirQualityResponse).join();
    }

    /**
     * Processes the JSON response from the AirVisual API to extract air quality data.
     * It parses the response into an {@code ApiResponse} object. If the response is
     * valid and contains the required data, it updates the UI. If the response structure
     * is invalid or parsing fails, it logs an error and displays a failure message on the UI.
     *
     * @param response the JSON string response from the AirVisual API.
     */
    private void processAirQualityResponse(String response) {
        Gson gson = new Gson();
        try {
            ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);
            if (apiResponse != null && apiResponse.getData() != null &&
                apiResponse.getData().getCurrent() != null) {
                Platform.runLater(() -> updateUIWithAirQualityData(apiResponse));
            } else {
                throw new Exception("Invalid API response structure.");
            }
        } catch (Exception e) {
            System.err.println("JSON Parsing Error: " + e.getMessage());
            Platform.runLater(() -> {
                root.getChildren().add(new Label("Failed to parse the air quality data."));
            });
        }
    }

    /**
     * Updates the UI with air quality data extracted from the provided ApiResponse.
     * This method creates labels for wind speed and direction, adds them to the display,
     * and shows the updated air quality information. If no weather data is available,
     * it displays a corresponding message.
     *
     * @param response the ApiResponse object containing air quality data.
     */
    private void updateUIWithAirQualityData(ApiResponse response) {
        VBox airQualityDisplay = new VBox(10);
        airQualityDisplay.setAlignment(Pos.CENTER);

        Weather weather = response.getData().getCurrent().getWeather();
        if (weather != null) {
            Label windSpeedLabel = new Label("Wind Speed: " + weather.getWs() + " m/s");
            Label windDirectionLabel = new Label("Wind Direction: " + weather.getWd() + "°");
            airQualityDisplay.getChildren().addAll(windSpeedLabel, windDirectionLabel);
        } else {
            airQualityDisplay.getChildren().add(new Label("No weather data available"));
        }

        root.getChildren().add(airQualityDisplay);
    }

    /**
     * Returns the list of forecasts.
     * This list contains the forecast data objects that have been previously set or fetched.
     *
     * @return the list of forecast data objects.
     */
    public List<Forecast> getForecasts() {
        return forecasts;
    }


    /**
     * Sets the list of forecasts with the provided list of forecast data.
     * This method updates the internal list of forecasts used by the application.
     *
     * @param forecasts the list of forecasts to set.
     */
    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }




} // ApiApp
