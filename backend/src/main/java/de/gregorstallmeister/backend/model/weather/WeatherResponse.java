package de.gregorstallmeister.backend.model.weather;

public record WeatherResponse(
        String positionInGrid,
        String time,
        int interval,
        String temperature,
        String tempApparent,
        String precipitation,
        String relative_humidity,
        String windSpeed,
        int windDirection,
        String windGusts,
        String cloud_cover,
        String surface_pressure
) {
}
