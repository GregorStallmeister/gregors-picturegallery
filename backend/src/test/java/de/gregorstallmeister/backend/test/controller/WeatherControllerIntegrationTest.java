package de.gregorstallmeister.backend.test.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getWeather() {
        // given
        String positionInGrid = "latitude=48.8109&longitude=9.3644";

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/" + positionInGrid))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.tempApparent").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.rain").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.humidity").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windSpeed").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windDirection").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.windGusts").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.snowHeight").isNotEmpty());
        }
        catch (Exception e) {
            Assertions.fail();
        }
    }
}
