package de.gregorstallmeister.backend.model.weather;

public record WeatherResponseDto(
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
