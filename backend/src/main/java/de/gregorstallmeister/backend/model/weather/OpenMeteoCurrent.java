package de.gregorstallmeister.backend.model.weather;

public record OpenMeteoCurrent(
        String time,
        int interval,
        double temperature_2m,
        int relative_humidity_2m,
        double wind_speed_10m,
        int wind_direction_10m,
        double rain,
        double snowfall,
        double apparent_temperature,
        int is_day,
        int cloud_cover,
        double precipitation,
        double showers,
        int weather_code,
        double pressure_msl,
        double surface_pressure,
        double wind_gusts_10m
) {
}
