package de.gregorstallmeister.backend.test.model.weather;

import de.gregorstallmeister.backend.model.weather.WeatherResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherResponseDtoTest {

    @Test
    void constructorTest() {

        // given
        String temperature = "22 °C";
        String tempApparent = "15 °C";
        String rain = "10 mm";
        String humidity = "73 %";
        String windSpeed = "20 km/h";
        int windDirection = 271;
        String windGusts = "55 km/h";
        String snowHeight = "0 cm";

        // when
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto(temperature, tempApparent, rain,
                humidity, windSpeed, windDirection, windGusts, snowHeight);

        // then
        assertEquals(temperature, weatherResponseDto.temperature());
        assertEquals(tempApparent, weatherResponseDto.tempApparent());
        assertEquals(rain, weatherResponseDto.rain());
        assertEquals(humidity, weatherResponseDto.humidity());
        assertEquals(windSpeed, weatherResponseDto.windSpeed());
        assertEquals(windDirection, weatherResponseDto.windDirection());
        assertEquals(windGusts, weatherResponseDto.windGusts());
        assertEquals(snowHeight, weatherResponseDto.snowHeight());
    }
}
