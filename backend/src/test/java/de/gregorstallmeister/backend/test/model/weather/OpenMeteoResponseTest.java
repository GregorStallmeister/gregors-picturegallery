package de.gregorstallmeister.backend.test.model.weather;

import de.gregorstallmeister.backend.model.weather.OpenMeteoCurrent;
import de.gregorstallmeister.backend.model.weather.OpenMeteoCurrentUnits;
import de.gregorstallmeister.backend.model.weather.OpenMeteoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenMeteoResponseTest {

    @Test
    void constructorTest() {

        // given
        double latitude = 53.739998;
        double longitude = 7.4999995;
        double generationtimeMs = 0.16224384307861328;
        int utcOffsetSeconds = 0;
        String timezone = "GMT";
        String timezoneAbbreviation = "GMTAbbreviation";
        double elevation = 1.0;

        String time = "iso8601";
        String interval = "seconds";
        String temperature2M = "°C";
        String relativeHumidity2M = "%";
        String windSpeed10M = "km/h";
        String windDirection10M = "°";
        String rain = "mmRain";
        String snowfall = "cm";
        String apparentTemperature = "°C";
        String isDay = "";
        String cloudCover = "%";
        String precipitation = "mmPrecipitation";
        String showers = "mmShowers";
        String weatherCode = "wmo code";
        String pressureMsl = "hPaPressure_msl";
        String surfacePressure = "hPaSurface_pressure";
        String windGusts10M = "km/h";

        String timeC = "2025-04-14T08:30";
        int intervalC = 900;
        double temperature2MC = 10.4;
        int relativeHumidity2MC = 83;
        double windSpeed10MC = 20.5;
        int windDirection10MC = 259;
        double rainC = 0.00;
        double snowfallC = 0.01;
        double apparentTemperatureC = 6.7;
        int isDayC = 1;
        int cloudCoverC = 71;
        double precipitationC = 0.02;
        double showersC = 0.03;
        int weatherCodeC = 2;
        double pressureMslC = 1009.4;
        double surfacePressureC = 1009.3;
        double windGusts10MC = 35.3;

        // when
        OpenMeteoCurrentUnits openMeteoCurrentUnits = new OpenMeteoCurrentUnits(time, interval, temperature2M, relativeHumidity2M,
                windSpeed10M, windDirection10M, rain, snowfall, apparentTemperature, isDay, cloudCover, precipitation,
                showers, weatherCode, pressureMsl, surfacePressure, windGusts10M);
        OpenMeteoCurrent openMeteoCurrent = new OpenMeteoCurrent(timeC, intervalC, temperature2MC, relativeHumidity2MC,
                windSpeed10MC, windDirection10MC, rainC, snowfallC, apparentTemperatureC, isDayC, cloudCoverC, precipitationC,
                showersC, weatherCodeC, pressureMslC, surfacePressureC, windGusts10MC);
        OpenMeteoResponse openMeteoResponse = new OpenMeteoResponse(latitude, longitude, generationtimeMs, utcOffsetSeconds,
                timezone, timezoneAbbreviation, elevation, openMeteoCurrentUnits, openMeteoCurrent);

        // then
        assertEquals(latitude, openMeteoResponse.latitude());
        assertEquals(longitude, openMeteoResponse.longitude());
        assertEquals(generationtimeMs, openMeteoResponse.generationtime_ms());
        assertEquals(utcOffsetSeconds, openMeteoResponse.utc_offset_seconds());
        assertEquals(timezone, openMeteoResponse.timezone());
        assertEquals(timezoneAbbreviation, openMeteoResponse.timezone_abbreviation());
        assertEquals(openMeteoCurrentUnits, openMeteoResponse.current_units());
        assertEquals(openMeteoCurrent, openMeteoResponse.current());
    }
}
