package de.gregorstallmeister.backend.model.weather;

public record WeatherResponseDto(
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
