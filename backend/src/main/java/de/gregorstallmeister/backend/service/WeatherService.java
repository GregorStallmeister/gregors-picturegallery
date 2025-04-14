package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.model.weather.OpenMeteoResponse;
import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {

    private final RestClient restClient;

    private static final String REQUEST_STRING =
            "forecast?positionInGrid&models=icon_seamless&current=temperature_2m,relative_humidity_2m," +
                    "wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature,is_day," +
                    "cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure,wind_gusts_10m";

    WeatherService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://api.open-meteo.com/v1").build();
    }

    public OpenMeteoResponse getWeatherRaw(String positionInGrid) {
        return restClient.get().uri("/" + REQUEST_STRING.replace("positionInGrid", positionInGrid))
                .retrieve().body(OpenMeteoResponse.class);
    }

    public WeatherResponse getWeather(String positionInGrid) {
        OpenMeteoResponse openMeteoResponse = this.getWeatherRaw(positionInGrid);

        String time = openMeteoResponse.current().time();
        int interval = openMeteoResponse.current().interval();
        String temperature = openMeteoResponse.current().temperature_2m() + " " + openMeteoResponse.current_units().temperature_2m();
        String tempApparent = openMeteoResponse.current().apparent_temperature() + " " + openMeteoResponse.current_units().apparent_temperature();
        String rain = openMeteoResponse.current().rain() + " " + openMeteoResponse.current_units().rain();
        String humidity = openMeteoResponse.current().relative_humidity_2m() + " " + openMeteoResponse.current_units().relative_humidity_2m();
        String windSpeed = openMeteoResponse.current().wind_speed_10m() + " " + openMeteoResponse.current_units().wind_speed_10m();
        int windDirection = openMeteoResponse.current().wind_direction_10m();
        String windGusts = openMeteoResponse.current().wind_gusts_10m() + " " + openMeteoResponse.current_units().wind_gusts_10m();
        String snowHeight = openMeteoResponse.current().snowfall() + " " + openMeteoResponse.current_units().snowfall();

        return new WeatherResponse(positionInGrid, time, interval, temperature, tempApparent, rain, humidity, windSpeed,
                windDirection, windGusts, snowHeight);
    }
}
