package de.gregorstallmeister.pg.backend.service;

import de.gregorstallmeister.pg.backend.model.weather.OpenMeteoResponse;
import de.gregorstallmeister.pg.backend.model.weather.WeatherResponse;
import de.gregorstallmeister.pg.backend.repository.WeatherResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WeatherService {

    private final RestClient restClient;
    private final WeatherResponseRepository weatherResponseRepository;

    private static final String REQUEST_STRING =
            "forecast?positionInGrid&models=icon_seamless&current=temperature_2m,relative_humidity_2m," +
                    "wind_speed_10m,wind_direction_10m,rain,snowfall,apparent_temperature,is_day," +
                    "cloud_cover,precipitation,showers,weather_code,pressure_msl,surface_pressure,wind_gusts_10m";

    public WeatherService(RestClient.Builder builder, WeatherResponseRepository weatherResponseRepository) {
        this.restClient = builder.baseUrl("https://api.open-meteo.com/v1").build();
        this.weatherResponseRepository = weatherResponseRepository;
    }

    public OpenMeteoResponse getWeatherRaw(String positionInGrid) {
        String[] possibleMatches = new String[2];
        possibleMatches[0] = "grütze"; // for testing a case, which is accepted by open-meteo
        possibleMatches[1] = "latitude=[\\d]+\\.[\\d]+&longitude=[\\d]+\\.[\\d]+";

        boolean isMatch = false;

        String requestString = REQUEST_STRING;
        for (String possibleMatch : possibleMatches) {
            if (positionInGrid.matches(possibleMatch)) {
                requestString = requestString.replace("positionInGrid", positionInGrid);
                isMatch = true;
                break;
            }
        }

        if (isMatch) {
            return restClient.get().uri("/" + requestString)
                    .retrieve()
                    .onStatus(status -> status.value() == 400, (request, response) -> {
                        throw new NoSuchElementException("No weather available for position in grid: " + positionInGrid
                                + " - The cause was: " + response.getStatusText() + " (status text in response from Open Meteo API)");
                    })
                    .body(OpenMeteoResponse.class);
        }

        return null;
    }

    public WeatherResponse getWeather(String positionInGrid) {
        Optional<WeatherResponse> optionalWeatherResponse = weatherResponseRepository.findById(positionInGrid);

        if (optionalWeatherResponse.isPresent()) {
            WeatherResponse weatherResponse = optionalWeatherResponse.get();
            Instant instant = Instant.parse(weatherResponse.time() + ":00.000000000Z");

            if (instant.plusSeconds(weatherResponse.interval()).compareTo(Instant.now()) > 0) {
                return weatherResponse;
            }
        }

        OpenMeteoResponse openMeteoResponse = this.getWeatherRaw(positionInGrid);

        if (openMeteoResponse == null) {
            throw new NoSuchElementException("No weather available for position in grid: " + positionInGrid +
                    " (Open Meto API returned null)");
        }

        WeatherResponse weatherResponse = WeatherResponse.builder()
                .positionInGrid(positionInGrid)
                .time(openMeteoResponse.current().time())
                .interval(openMeteoResponse.current().interval())
                .temperature(openMeteoResponse.current().temperature_2m() + " " + openMeteoResponse.current_units().temperature_2m())
                .tempApparent(openMeteoResponse.current().apparent_temperature() + " " + openMeteoResponse.current_units().apparent_temperature())
                .precipitation(openMeteoResponse.current().precipitation() + " " + openMeteoResponse.current_units().precipitation())
                .relativeHumidity(openMeteoResponse.current().relative_humidity_2m() + " " + openMeteoResponse.current_units().relative_humidity_2m())
                .windSpeed(openMeteoResponse.current().wind_speed_10m() + " " + openMeteoResponse.current_units().wind_speed_10m())
                .windDirection(openMeteoResponse.current().wind_direction_10m())
                .windGusts(openMeteoResponse.current().wind_gusts_10m() + " " + openMeteoResponse.current_units().wind_gusts_10m())
                .cloudCover(openMeteoResponse.current().cloud_cover() + " " + openMeteoResponse.current_units().cloud_cover())
                .surfacePressure(openMeteoResponse.current().surface_pressure() + " " + openMeteoResponse.current_units().surface_pressure())
                .pressureMsl(openMeteoResponse.current().pressure_msl() + " " + openMeteoResponse.current_units().pressure_msl())
                .build();

        weatherResponseRepository.save(weatherResponse);
        return weatherResponse;
    }
}
