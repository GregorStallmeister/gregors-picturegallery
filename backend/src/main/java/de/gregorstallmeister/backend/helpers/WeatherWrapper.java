package de.gregorstallmeister.backend.helpers;

import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.backend.model.weather.WeatherResponseDto;

public class WeatherWrapper {

    private WeatherWrapper() {}

    public static WeatherResponseDto wrapForGet(WeatherResponse weatherResponse){
        return new WeatherResponseDto(weatherResponse.temperature(), weatherResponse.tempApparent(),
                weatherResponse.precipitation(), weatherResponse.relative_humidity(), weatherResponse.windSpeed(), weatherResponse.windDirection(),
                weatherResponse.windGusts(), weatherResponse.cloud_cover(), weatherResponse.surface_pressure());
    }
}
