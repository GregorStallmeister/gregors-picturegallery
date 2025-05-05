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
        String pressureMsl = "998 hPa";

        // when
        WeatherResponseDto weatherResponseDto = new WeatherResponseDto(temperature, tempApparent, precipitation,
                relativeHumidity, windSpeed, windDirection, windGusts, cloudCover, surfacePressure, pressureMsl);

        // then
        assertEquals(temperature, weatherResponseDto.temperature());
        assertEquals(tempApparent, weatherResponseDto.tempApparent());
        assertEquals(precipitation, weatherResponseDto.precipitation());
        assertEquals(relativeHumidity, weatherResponseDto.relativeHumidity());
        assertEquals(windSpeed, weatherResponseDto.windSpeed());
        assertEquals(windDirection, weatherResponseDto.windDirection());
        assertEquals(windGusts, weatherResponseDto.windGusts());
        assertEquals(cloudCover, weatherResponseDto.cloudCover());
        assertEquals(surfacePressure, weatherResponseDto.surfacePressure());
        assertEquals(pressureMsl, weatherResponseDto.pressureMsl());
    }

    @Test
    void builderTest() {

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
        String pressureMsl = "998 hPa";

        // when
        WeatherResponseDto weatherResponseDto = WeatherResponseDto.builder()
                .temperature(temperature)
                .tempApparent(tempApparent)
                .precipitation(precipitation)
                .relativeHumidity(relativeHumidity)
                .windSpeed(windSpeed)
                .windDirection(windDirection)
                .windGusts(windGusts)
                .cloudCover(cloudCover)
                .surfacePressure(surfacePressure)
                .pressureMsl(pressureMsl)
                .build();

        // then
        assertEquals(temperature, weatherResponseDto.temperature());
        assertEquals(tempApparent, weatherResponseDto.tempApparent());
        assertEquals(precipitation, weatherResponseDto.precipitation());
        assertEquals(relativeHumidity, weatherResponseDto.relativeHumidity());
        assertEquals(windSpeed, weatherResponseDto.windSpeed());
        assertEquals(windDirection, weatherResponseDto.windDirection());
        assertEquals(windGusts, weatherResponseDto.windGusts());
        assertEquals(cloudCover, weatherResponseDto.cloudCover());
        assertEquals(surfacePressure, weatherResponseDto.surfacePressure());
        assertEquals(pressureMsl, weatherResponseDto.pressureMsl());
    }
}
