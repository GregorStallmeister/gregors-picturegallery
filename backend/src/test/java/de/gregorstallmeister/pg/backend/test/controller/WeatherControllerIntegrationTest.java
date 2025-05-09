package de.gregorstallmeister.pg.backend.test.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class WeatherControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockRestServiceServer mockRestServiceServer;

    @Test
    @DirtiesContext
    void getWeather() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature," +
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
                                                          "rain": "mm",
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
                                                          "rain": 0.00,
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

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.tempApparent").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.precipitation").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.relativeHumidity").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windSpeed").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windDirection").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windGusts").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.cloudCover").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.surfacePressure").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.pressureMsl").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                                "temperature": "12.1 °C",
                                "tempApparent": "11.8 °C",
                                "precipitation": "0.0 mm",
                                "relativeHumidity": "83 %",
                                "windSpeed": "1.1 km/h",
                                "windDirection": 90,
                                "windGusts": "4.3 km/h",
                                "cloudCover": "100 %",
                                "surfacePressure": "972.5 hPa",
                                "pressureMsl": "1000.1 hPa"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getWeatherWithGruetze() {
        // given
        String positionInGrid = "grütze";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "gr%C3%BCtze&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: No weather available for position in grid: grütze (Open Meto API returned null)"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getWeatherWithMist() {
        // given
        String positionInGrid = "mist";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "mist&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,precipitation,snowfall,apparent_temperature," +
                        "is_day,cloudCover,precipitation,showers,weather_code,pressureMsl,surfacePressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: No weather available for position in grid: mist (Open Meto API returned null)"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getWeatherWithInadmissibleLatitude() {
        // given
        String positionInGrid = "latitude=4800.8109&longitude=9.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=4800.8109&longitude=9.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: No weather available for position in grid: latitude=4800.8109&longitude=9.3644 (Open Meto API returned null)"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getWeatherWithInadmissibleLongitude() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=900.3644";
        mockRestServiceServer.expect(requestTo("https://api.open-meteo.com/v1/forecast?" +
                        "latitude=48.8109&longitude=900.3644&models=icon_seamless&current=temperature_2m," +
                        "relative_humidity_2m,wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature," +
                        "is_day,cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure," +
                        "wind_gusts_10m"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("", MediaType.APPLICATION_JSON));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: No weather available for position in grid: latitude=48.8109&longitude=900.3644 (Open Meto API returned null)"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
