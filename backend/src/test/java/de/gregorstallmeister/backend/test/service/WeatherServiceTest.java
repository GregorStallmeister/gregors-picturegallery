package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.model.weather.OpenMeteoResponse;
import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.backend.repository.WeatherResponseRepository;
import de.gregorstallmeister.backend.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class WeatherServiceTest {


    // The following 4 lines are from a refactoring with help by my coach Florian.
    // Many thanks to Florian for this and many more teaching and coaching, which cannot be overvalued!
    // My best wishes to him!
    WeatherResponseRepository mockWeatherResponseRepository = mock(WeatherResponseRepository.class);
    RestClient.Builder builder = RestClient.builder();
    MockRestServiceServer mockRestServiceServer = MockRestServiceServer.bindTo(builder).build();
    WeatherService weatherService = new WeatherService(builder, mockWeatherResponseRepository);

    @Test
    void getWeatherRaw() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "latitude": 48.72,
                                "longitude": 9.359999,
                                "generationtime_ms": 0.09846687316894531,
                                "utc_offset_seconds": 0,
                                "timezone": "GMT",
                                "timezone_abbreviation": "GMT",
                                "elevation": 234.0,
                                                      "current_units": {
                                                          "time": "iso8601",
                                                          "interval": "seconds",
                                                          "temperature_2m": "°C",
                                                          "relative_humidity_2m": "%",
                                                          "wind_speed_10m": "km/h",
                                                          "wind_direction_10m": "°",
                                                          "precipitation": "mm",
                                                          "snowfall": "cm",
                                                          "apparent_temperature": "°C",
                                                          "is_day": "",
                                                          "cloud_cover": "%",
                                                          "precipitation": "mm",
                                                          "showers": "mm",
                                                          "weather_code": "wmo code",
                                                          "pressure_msl": "hPa",
                                                          "surface_pressure": "hPa",
                                                          "wind_gusts_10m": "km/h"
                                                      },
                                                      "current": {
                                                          "time": "2025-04-15T07:00",
                                                          "interval": 900,
                                                          "temperature_2m": 12.1,
                                                          "relative_humidity_2m": 83,
                                                          "wind_speed_10m": 1.1,
                                                          "wind_direction_10m": 90,
                                                          "precipitation": 0.00,
                                                          "snowfall": 0.00,
                                                          "apparent_temperature": 11.8,
                                                          "is_day": 1,
                                                          "cloud_cover": 100,
                                                          "precipitation": 0.00,
                                                          "showers": 0.00,
                                                          "weather_code": 3,
                                                          "pressure_msl": 1000.1,
                                                          "surface_pressure": 972.5,
                                                          "wind_gusts_10m": 4.3
                                                      }
                        }
                        """, MediaType.APPLICATION_JSON));

        // when
        OpenMeteoResponse openMeteoResponse = weatherService.getWeatherRaw(positionInGrid);

        // then
        assertNotNull(openMeteoResponse);
        assertEquals(48.72, openMeteoResponse.latitude());
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
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "gr%C3%BCtze&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when
        OpenMeteoResponse openMeteoResponse = weatherService.getWeatherRaw(positionInGrid);

        // then
        assertNull(openMeteoResponse);
    }

    @Test
    void getWeatherRawWithMist() {
        // given
        String positionInGrid = "mist";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "gr%C3%BCtze&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when
        OpenMeteoResponse openMeteoResponse = weatherService.getWeatherRaw(positionInGrid);

        // then
        assertNull(openMeteoResponse);
    }

    @Test
    void getWeatherRawWithInadmissibleLatitude() {
        // given
        String positionInGrid = "latitude=4800.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=4800.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        // when
        try {
            weatherService.getWeatherRaw(positionInGrid);
        } catch (Exception e) {
            // then
            assertInstanceOf(NoSuchElementException.class, e);
            assertTrue(e.getMessage().startsWith("No weather available for position in grid: latitude=4800.8109&longitude=9.3644"));
            assertTrue(e.getMessage().matches(".*The cause was: .*"));
        }
    }

    @Test
    void getWeatherRawWithInadmissibleLongitude() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=900.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=900.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        // when
        try {
            weatherService.getWeatherRaw(positionInGrid);
        } catch (Exception e) {
            // then
            assertInstanceOf(NoSuchElementException.class, e);
            assertTrue(e.getMessage().startsWith("No weather available for position in grid: latitude=48.8109&longitude=900.3644"));
            assertTrue(e.getMessage().matches(".*The cause was: .*"));
        }
    }

    @Test
    void getWeather() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "latitude": 48.72,
                                "longitude": 9.359999,
                                "generationtime_ms": 0.09846687316894531,
                                "utc_offset_seconds": 0,
                                "timezone": "GMT",
                                "timezone_abbreviation": "GMT",
                                "elevation": 234.0,
                                                      "current_units": {
                                                          "time": "iso8601",
                                                          "interval": "seconds",
                                                          "temperature_2m": "°C",
                                                          "relative_humidity_2m": "%",
                                                          "wind_speed_10m": "km/h",
                                                          "wind_direction_10m": "°",
                                                          "precipitation": "mm",
                                                          "snowfall": "cm",
                                                          "apparent_temperature": "°C",
                                                          "is_day": "",
                                                          "cloud_cover": "%",
                                                          "precipitation": "mm",
                                                          "showers": "mm",
                                                          "weather_code": "wmo code",
                                                          "pressure_msl": "hPa",
                                                          "surface_pressure": "hPa",
                                                          "wind_gusts_10m": "km/h"
                                                      },
                                                      "current": {
                                                          "time": "2025-04-15T07:00",
                                                          "interval": 900,
                                                          "temperature_2m": 12.1,
                                                          "relative_humidity_2m": 83,
                                                          "wind_speed_10m": 1.1,
                                                          "wind_direction_10m": 90,
                                                          "precipitation": 0.00,
                                                          "snowfall": 0.00,
                                                          "apparent_temperature": 11.8,
                                                          "is_day": 1,
                                                          "cloud_cover": 100,
                                                          "precipitation": 0.00,
                                                          "showers": 0.00,
                                                          "weather_code": 3,
                                                          "pressure_msl": 1000.1,
                                                          "surface_pressure": 972.5,
                                                          "wind_gusts_10m": 4.3
                                                      }
                        }
                        """, MediaType.APPLICATION_JSON));

        // when
        WeatherResponse weatherResponse = weatherService.getWeather(positionInGrid);

        // then
        assertNotNull(weatherResponse);
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(900, weatherResponse.interval());
        assertEquals("1.1 km/h", weatherResponse.windSpeed());
    }

    @Test
    void getWeatherWhenNotPresent() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
//        WeatherService weatherService1 = new WeatherService(RestClient.builder(), mockWeatherResponseRepository);
        when(mockWeatherResponseRepository.findById(positionInGrid)).thenReturn(Optional.empty());
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "latitude": 48.72,
                                "longitude": 9.359999,
                                "generationtime_ms": 0.09846687316894531,
                                "utc_offset_seconds": 0,
                                "timezone": "GMT",
                                "timezone_abbreviation": "GMT",
                                "elevation": 234.0,
                                                      "current_units": {
                                                          "time": "iso8601",
                                                          "interval": "seconds",
                                                          "temperature_2m": "°C",
                                                          "relative_humidity_2m": "%",
                                                          "wind_speed_10m": "km/h",
                                                          "wind_direction_10m": "°",
                                                          "precipitation": "mm",
                                                          "snowfall": "cm",
                                                          "apparent_temperature": "°C",
                                                          "is_day": "",
                                                          "cloud_cover": "%",
                                                          "precipitation": "mm",
                                                          "showers": "mm",
                                                          "weather_code": "wmo code",
                                                          "pressure_msl": "hPa",
                                                          "surface_pressure": "hPa",
                                                          "wind_gusts_10m": "km/h"
                                                      },
                                                      "current": {
                                                          "time": "2025-04-15T07:00",
                                                          "interval": 900,
                                                          "temperature_2m": 12.1,
                                                          "relative_humidity_2m": 83,
                                                          "wind_speed_10m": 1.1,
                                                          "wind_direction_10m": 90,
                                                          "precipitation": 0.00,
                                                          "snowfall": 0.00,
                                                          "apparent_temperature": 11.8,
                                                          "is_day": 1,
                                                          "cloud_cover": 100,
                                                          "precipitation": 0.00,
                                                          "showers": 0.00,
                                                          "weather_code": 3,
                                                          "pressure_msl": 1000.1,
                                                          "surface_pressure": 972.5,
                                                          "wind_gusts_10m": 4.3
                                                      }
                        }
                        """, MediaType.APPLICATION_JSON));

        // when
        WeatherResponse weatherResponse = weatherService.getWeather(positionInGrid);

        // then
        verify(mockWeatherResponseRepository).findById(positionInGrid);
        assertNotNull(weatherResponse);
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(900, weatherResponse.interval());
        assertEquals("1.1 km/h", weatherResponse.windSpeed());
    }

    @Test
    void getWeatherWhenExpired() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "latitude": 48.72,
                                "longitude": 9.359999,
                                "generationtime_ms": 0.09846687316894531,
                                "utc_offset_seconds": 0,
                                "timezone": "GMT",
                                "timezone_abbreviation": "GMT",
                                "elevation": 234.0,
                                                      "current_units": {
                                                          "time": "iso8601",
                                                          "interval": "seconds",
                                                          "temperature_2m": "°C",
                                                          "relative_humidity_2m": "%",
                                                          "wind_speed_10m": "km/h",
                                                          "wind_direction_10m": "°",
                                                          "precipitation": "mm",
                                                          "snowfall": "cm",
                                                          "apparent_temperature": "°C",
                                                          "is_day": "",
                                                          "cloud_cover": "%",
                                                          "precipitation": "mm",
                                                          "showers": "mm",
                                                          "weather_code": "wmo code",
                                                          "pressure_msl": "hPa",
                                                          "surface_pressure": "hPa",
                                                          "wind_gusts_10m": "km/h"
                                                      },
                                                      "current": {
                                                          "time": "2025-04-15T07:00",
                                                          "interval": 900,
                                                          "temperature_2m": 12.1,
                                                          "relative_humidity_2m": 83,
                                                          "wind_speed_10m": 1.1,
                                                          "wind_direction_10m": 90,
                                                          "precipitation": 0.00,
                                                          "snowfall": 0.00,
                                                          "apparent_temperature": 11.8,
                                                          "is_day": 1,
                                                          "cloud_cover": 100,
                                                          "precipitation": 0.00,
                                                          "showers": 0.00,
                                                          "weather_code": 3,
                                                          "pressure_msl": 1000.1,
                                                          "surface_pressure": 972.5,
                                                          "wind_gusts_10m": 4.3
                                                      }
                        }
                        """, MediaType.APPLICATION_JSON));

        // when
        WeatherResponse weatherResponse = weatherService.getWeather(positionInGrid);

        // then
        assertNotNull(weatherResponse);
        assertEquals(positionInGrid, weatherResponse.positionInGrid());
        assertEquals(900, weatherResponse.interval());
        assertEquals("1.1 km/h", weatherResponse.windSpeed());
    }

    @Test
    void getWeatherWithGruetze() {
        // given
        String positionInGrid = "grütze";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "gr%C3%BCtze&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            weatherService.getWeather(positionInGrid);
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            assertEquals("No weather available for position in grid: grütze", e.getMessage());
        }
    }

    @Test
    void getWeatherWithMist() {
        // given
        String positionInGrid = "mist";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "gr%C3%BCtze&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            weatherService.getWeather(positionInGrid);
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            assertEquals("No weather available for position in grid: mist", e.getMessage());
        }
    }

    @Test
    void getWeatherWithInadmissibleLatitude() {
        // given
        String positionInGrid = "latitude=4800.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=4800.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        // when
        try {
            weatherService.getWeather(positionInGrid);
        } catch (Exception e) {
            // then
            assertInstanceOf(NoSuchElementException.class, e);
            assertTrue(e.getMessage().startsWith("No weather available for position in grid: latitude=4800.8109&longitude=9.3644"));
            assertTrue(e.getMessage().matches(".*The cause was: .*"));
        }
    }

    @Test
    void getWeatherWithInadmissibleLongitude() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=900.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=900.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        // when
        try {
            weatherService.getWeather(positionInGrid);
        } catch (Exception e) {
            // then
            assertInstanceOf(NoSuchElementException.class, e);
            assertTrue(e.getMessage().startsWith("No weather available for position in grid: latitude=48.8109&longitude=900.3644"));
            assertTrue(e.getMessage().matches(".*The cause was: .*"));
        }
    }
}
