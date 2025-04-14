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
        String rain = "10 mm";
        String humidity = "73 %";
        String windSpeed = "20 km/h";
        int windDirection = 271;
        String windGusts = "55 km/h";
        String snowHeight = "0 cm";

        // when
        WeatherResponse weatherResponse = new WeatherResponse(positionInGrid, time, interval, temperature, tempApparent, rain,
                humidity, windSpeed, windDirection, windGusts, snowHeight);

        // then
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(time, weatherResponse.time());
        assertEquals(interval, weatherResponse.interval());
        assertEquals(temperature, weatherResponse.temperature());
        assertEquals(tempApparent, weatherResponse.tempApparent());
        assertEquals(rain, weatherResponse.rain());
        assertEquals(humidity, weatherResponse.humidity());
        assertEquals(windSpeed, weatherResponse.windSpeed());
        assertEquals(windDirection, weatherResponse.windDirection());
        assertEquals(windGusts, weatherResponse.windGusts());
        assertEquals(snowHeight, weatherResponse.snowHeight());
    }
}
