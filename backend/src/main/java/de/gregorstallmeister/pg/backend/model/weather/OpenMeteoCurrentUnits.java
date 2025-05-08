package de.gregorstallmeister.pg.backend.model.weather;

public record OpenMeteoCurrentUnits(
        String time,
        String interval,
        String temperature_2m,
        String relative_humidity_2m,
        String wind_speed_10m,
        String wind_direction_10m,
        String rain,
        String snowfall,
        String apparent_temperature,
        String is_day,
        String cloud_cover,
        String precipitation,
        String showers,
        String weather_code,
        String pressure_msl,
        String surface_pressure,
        String wind_gusts_10m
) {
}
