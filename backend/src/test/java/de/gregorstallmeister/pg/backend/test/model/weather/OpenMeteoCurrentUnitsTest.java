package de.gregorstallmeister.pg.backend.test.model.weather;

import de.gregorstallmeister.pg.backend.model.weather.OpenMeteoCurrentUnits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenMeteoCurrentUnitsTest {

    @Test
    void constructorTest() {

        // given
        String time = "iso8601";
        String interval = "seconds";
        String temperature2M = "°C";
        String relativeHumidity2M = "%";
        String windSpeed10M = "km/h";
        String windDirection10M = "°";
        String rain = "mmRain";
        String snowfall = "cm";
        String apparentTemperature = "°C";
        String isDay = "";
        String cloudCover = "%";
        String precipitation = "mmPrecipitation";
        String showers = "mmShowers";
        String weatherCode = "wmo code";
        String pressureMsl = "hPaPressure_msl";
        String surfacePressure = "hPaSurface_pressure";
        String windGusts10M = "km/h";

        // when
        OpenMeteoCurrentUnits openMeteoCurrentUnits = new OpenMeteoCurrentUnits(time, interval, temperature2M, relativeHumidity2M,
                windSpeed10M, windDirection10M, rain, snowfall, apparentTemperature, isDay, cloudCover, precipitation,
                showers, weatherCode, pressureMsl, surfacePressure, windGusts10M);

        // then
        assertEquals(time, openMeteoCurrentUnits.time());
        assertEquals(interval, openMeteoCurrentUnits.interval());
        assertEquals(temperature2M, openMeteoCurrentUnits.temperature_2m());
        assertEquals(relativeHumidity2M, openMeteoCurrentUnits.relative_humidity_2m());
        assertEquals(windSpeed10M, openMeteoCurrentUnits.wind_speed_10m());
        assertEquals(windDirection10M, openMeteoCurrentUnits.wind_direction_10m());
        assertEquals(rain, openMeteoCurrentUnits.rain());
        assertEquals(snowfall, openMeteoCurrentUnits.snowfall());
        assertEquals(apparentTemperature, openMeteoCurrentUnits.apparent_temperature());
        assertEquals(isDay, openMeteoCurrentUnits.is_day());
        assertEquals(cloudCover, openMeteoCurrentUnits.cloud_cover());
        assertEquals(precipitation, openMeteoCurrentUnits.precipitation());
        assertEquals(showers, openMeteoCurrentUnits.showers());
        assertEquals(weatherCode, openMeteoCurrentUnits.weather_code());
        assertEquals(pressureMsl, openMeteoCurrentUnits.pressure_msl());
        assertEquals(surfacePressure, openMeteoCurrentUnits.surface_pressure());
        assertEquals(windGusts10M, openMeteoCurrentUnits.wind_gusts_10m());
    }
}
