package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.WeatherWrapper;
import de.gregorstallmeister.backend.model.weather.WeatherResponseDto;
import de.gregorstallmeister.backend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/{positionInGrid}")
    @ResponseStatus(HttpStatus.OK) // 200
    public WeatherResponseDto getWeather (@PathVariable String positionInGrid) {
        return WeatherWrapper.wrapForGet(weatherService.getWeather(positionInGrid));
    }
}
