package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.model.weather.OpenMeteoResponse;
import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.backend.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherServiceTest {

    @Autowired
    WeatherService weatherService;

    @Test
    void getWeatherRaw() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";

        // when
        OpenMeteoResponse openMeteoResponse = weatherService.getWeatherRaw(positionInGrid);

        // then
        assertNotNull(openMeteoResponse);
        assertEquals(48.82, openMeteoResponse.latitude());
        assertEquals(9.359999, openMeteoResponse.longitude());
        assertEquals(0, openMeteoResponse.utc_offset_seconds());
        assertEquals("GMT", openMeteoResponse.timezone());
        assertNotNull(openMeteoResponse.current_units());
        assertEquals("seconds", openMeteoResponse.current_units().interval());
        assertEquals("°", openMeteoResponse.current_units().wind_direction_10m());
        assertEquals("cm", openMeteoResponse.current_units().snowfall());
        assertNotNull(openMeteoResponse.current());
        assertEquals(900, openMeteoResponse.current().interval());
    }

    @Test
    void getWeatherRawWithGruetze() {
        // given
        String positionInGrid = "grütze";

        // when
        OpenMeteoResponse openMeteoResponse = weatherService.getWeatherRaw(positionInGrid);

        // then
        assertNull(openMeteoResponse);
    }

    @Test
    void getWeather() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";

        // when
        WeatherResponse weatherResponse = weatherService.getWeather(positionInGrid);

        // then
        assertNotNull(weatherResponse);
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(900, weatherResponse.interval());
    }

    @Test
    void getWeatherWithGruetze() {
        // given
        String positionInGrid = "grütze";

        // when + then
        try {
            weatherService.getWeather(positionInGrid);
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            assertEquals("No weather available for position in grid: grütze", e.getMessage());
        }
    }
}
