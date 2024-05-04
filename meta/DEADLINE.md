# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub `https` URL to your repository.**

The AirDetails Application provides real-time air quality and weather data for selected locations. It utilizes the LocationIQ API for geocoding and the IQAir API to fetch air quality data, allowing users to view current wind speeds and directions after selecting a location. This combination of APIs helps users make informed decisions about outdoor activities based on air quality conditions.

https://github.com/ManishUGA/cs1302-api-app

## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### LocationIQ API

```
https://us1.locationiq.com/v1/search.php?key=pk.9e9dedacb2acce21c1dd1bcf779c542e&q={Location}&format=json
```

> This API is used to convert location names to geographic coordinates, essential for querying the IQAir API.

### IQAir API

```
http://api.airvisual.com/v2/nearest_city?lat={latitude}&lon={longitude}&key=7efd9977-ea62-4845-8581-bf14a6f6a62e
```

> This API provides detailed air quality reports including wind speed and direction, used directly to inform users about the current air conditions.

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?

Throughout this project, I learned how to integrate and manipulate data from multiple RESTful APIs effectively. The challenge of synchronizing asynchronous requests and updating the UI in a responsive manner was particularly enlightening.

## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

If starting over, I would implement a feature to automatically detect the user's location rather than selecting from preset locations. This would enhance user experience by providing immediate, relevant data without manual input.