package de.gregorstallmeister.backend.test.model.weather;

import de.gregorstallmeister.backend.model.weather.OpenMeteoCurrent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class OpenMeteoCurrentTest {

    @Test
    void constructorTest() {

        // given
        String time = "2025-04-14T08:30";
        int interval = 900;
        double temperature2M = 10.4;
        int relativeHumidity2M = 83;
        double windSpeed10M = 20.5;
        int windDirection10M = 259;
        double rain = 0.00;
        double snowfall = 0.01;
        double apparentTemperature = 6.7;
        int isDay = 1;
        int cloudCover = 71;
        double precipitation = 0.02;
        double showers = 0.03;
        int weatherCode = 2;
        double pressureMsl = 1009.4;
        double surfacePressure = 1009.3;
        double windGusts10M = 35.3;

        // when
        OpenMeteoCurrent openMeteoCurrent = new OpenMeteoCurrent(time, interval, temperature2M, relativeHumidity2M,
                windSpeed10M, windDirection10M, rain, snowfall, apparentTemperature, isDay, cloudCover, precipitation,
                showers, weatherCode, pressureMsl, surfacePressure, windGusts10M);


        // then
        assertEquals(time, openMeteoCurrent.time());
        assertEquals(interval, openMeteoCurrent.interval());
        assertEquals(temperature2M, openMeteoCurrent.temperature_2m());
        assertEquals(relativeHumidity2M, openMeteoCurrent.relative_humidity_2m());
        assertEquals(windSpeed10M, openMeteoCurrent.wind_speed_10m());
        assertEquals(windDirection10M, openMeteoCurrent.wind_direction_10m());
        assertEquals(rain, openMeteoCurrent.rain());
        assertEquals(snowfall, openMeteoCurrent.snowfall());
        assertEquals(apparentTemperature, openMeteoCurrent.apparent_temperature());
        assertEquals(isDay, openMeteoCurrent.is_day());
        assertEquals(cloudCover, openMeteoCurrent.cloud_cover());
        assertEquals(precipitation, openMeteoCurrent.precipitation());
        assertEquals(showers, openMeteoCurrent.showers());
        assertEquals(weatherCode, openMeteoCurrent.weather_code());
        assertEquals(pressureMsl, openMeteoCurrent.pressure_msl());
        assertEquals(surfacePressure, openMeteoCurrent.surface_pressure());
        assertEquals(windGusts10M, openMeteoCurrent.wind_gusts_10m());
    }
}
