package de.gregorstallmeister.backend.model.weather;

public record WeatherResponse(
        String positionInGrid,
        String time,
        int interval,
        String temperature,
        String tempApparent,
        String rain,
        String humidity,
        String windSpeed,
        int windDirection,
        String windGusts,
        String snowHeight
) {
}
