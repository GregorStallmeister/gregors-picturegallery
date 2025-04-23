package de.gregorstallmeister.backend.helpers;

import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.backend.model.weather.WeatherResponseDto;

public class WeatherWrapper {

    private WeatherWrapper() {}

    public static WeatherResponseDto wrapForGet(WeatherResponse weatherResponse){
        return WeatherResponseDto.builder()
                .temperature(weatherResponse.temperature())
                .tempApparent(weatherResponse.tempApparent())
                .precipitation(weatherResponse.precipitation())
                .relative_humidity(weatherResponse.relative_humidity())
                .windSpeed(weatherResponse.windSpeed())
                .windDirection(weatherResponse.windDirection())
                .windGusts(weatherResponse.windGusts())
                .cloud_cover(weatherResponse.cloud_cover())
                .surface_pressure(weatherResponse.surface_pressure())
                .build();
    }
}
