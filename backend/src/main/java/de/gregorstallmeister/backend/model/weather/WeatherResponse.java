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
        String relative_humidity,
        String windSpeed,
        int windDirection,
        String windGusts,
        String cloud_cover,
        String surface_pressure
) {
}
