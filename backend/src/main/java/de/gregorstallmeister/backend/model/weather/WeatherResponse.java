package de.gregorstallmeister.backend.model.weather;


import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record WeatherResponse(
        @Id
        String positionInGrid,
        String time,
        long interval,
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
