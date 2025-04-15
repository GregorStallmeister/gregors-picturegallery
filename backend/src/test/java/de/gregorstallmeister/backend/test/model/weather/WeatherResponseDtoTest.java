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
        String precipitation = "10 mm";
        String relativeHumidity = "73 %";
        String windSpeed = "20 km/h";
        int windDirection = 271;
        String windGusts = "55 km/h";
        String cloudCover = "58 %";
        String surfacePressure = "1018 hPa";

        // when
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto(temperature, tempApparent, precipitation,
                relativeHumidity, windSpeed, windDirection, windGusts, cloudCover, surfacePressure);

        // then
        assertEquals(temperature, weatherResponseDto.temperature());
        assertEquals(tempApparent, weatherResponseDto.tempApparent());
        assertEquals(precipitation, weatherResponseDto.precipitation());
        assertEquals(relativeHumidity, weatherResponseDto.relative_humidity());
        assertEquals(windSpeed, weatherResponseDto.windSpeed());
        assertEquals(windDirection, weatherResponseDto.windDirection());
        assertEquals(windGusts, weatherResponseDto.windGusts());
        assertEquals(cloudCover, weatherResponseDto.cloud_cover());
        assertEquals(surfacePressure, weatherResponseDto.surface_pressure());
    }
}
