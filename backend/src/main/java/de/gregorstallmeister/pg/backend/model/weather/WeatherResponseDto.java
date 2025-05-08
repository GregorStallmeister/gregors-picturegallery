package de.gregorstallmeister.pg.backend.model.weather;

import lombok.Builder;

@Builder
public record WeatherResponseDto(
        String temperature,
        String tempApparent,
        String precipitation,
        String relativeHumidity,
        String windSpeed,
        int windDirection,
        String windGusts,
        String cloudCover,
        String surfacePressure,
        String pressureMsl
) {
}
