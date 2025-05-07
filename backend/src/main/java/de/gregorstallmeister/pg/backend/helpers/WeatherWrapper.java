package de.gregorstallmeister.pg.backend.helpers;

import de.gregorstallmeister.pg.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.pg.backend.model.weather.WeatherResponseDto;

public class WeatherWrapper {

    private WeatherWrapper() {}

    public static WeatherResponseDto wrapForGet(WeatherResponse weatherResponse){
        return WeatherResponseDto.builder()
                .temperature(weatherResponse.temperature())
                .tempApparent(weatherResponse.tempApparent())
                .precipitation(weatherResponse.precipitation())
                .relativeHumidity(weatherResponse.relativeHumidity())
                .windSpeed(weatherResponse.windSpeed())
                .windDirection(weatherResponse.windDirection())
                .windGusts(weatherResponse.windGusts())
                .cloudCover(weatherResponse.cloudCover())
                .surfacePressure(weatherResponse.surfacePressure())
                .pressureMsl(weatherResponse.pressureMsl())
                .build();
    }
}
