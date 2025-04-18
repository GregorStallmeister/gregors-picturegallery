package de.gregorstallmeister.backend.test.model.weather;

import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherResponseTest {

    @Test
    void constructorTest() {

        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
        String time = Instant.now().toString();
        int interval = 900;
        String temperature = "22 °C";
        String tempApparent = "15 °C";
        String precipitation = "10 mm";
        String relativeHumidity = "73 %";
        String windSpeed = "20 km/h";
        int windDirection = 271;
        String windGusts = "55 km/h";
        String cloudCover = "58 %";
        String surfacePressure = "1018 hPa";

        // when
        WeatherResponse weatherResponse = new WeatherResponse(positionInGrid, time, interval, temperature, tempApparent, precipitation,
                relativeHumidity, windSpeed, windDirection, windGusts, cloudCover, surfacePressure);

        // then
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(time, weatherResponse.time());
        assertEquals(interval, weatherResponse.interval());
        assertEquals(temperature, weatherResponse.temperature());
        assertEquals(tempApparent, weatherResponse.tempApparent());
        assertEquals(precipitation, weatherResponse.precipitation());
        assertEquals(relativeHumidity, weatherResponse.relative_humidity());
        assertEquals(windSpeed, weatherResponse.windSpeed());
        assertEquals(windDirection, weatherResponse.windDirection());
        assertEquals(windGusts, weatherResponse.windGusts());
        assertEquals(cloudCover, weatherResponse.cloud_cover());
        assertEquals(surfacePressure, weatherResponse.surface_pressure());
    }
}
