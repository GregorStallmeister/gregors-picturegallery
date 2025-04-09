package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.backend.helpers.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleNoSuchElementException(NoSuchElementException ex) {
        return new ErrorMessage("An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomAuthenticationException.class)
    public ErrorMessage handleCustomAuthenticationException(CustomAuthenticationException ex) {
        return new ErrorMessage("An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException ex) {
        String returnMessage = "<html><head><title>Gregors Fotogalerie - Fehlerseite</title></head>" +
                "<body style=\"background-color:#F6F2B4;\">Gregors Fotogalerie - schön, dass Sie da sind und herzlich willkommen!" +
                "<br><br>Diese Seite ist leider nicht an allen Stellen kompatibel zum Neu-Laden per Browser-Button." +
                "<br>Bitte rufen Sie sie neu auf, durch Klick auf den Link, über den Sie gekommen sind.";
        if (ex.getMessage().contains("No static resource ")) {
            String badPartOfUrl = ex.getMessage().replaceFirst("No static resource ", "");
            badPartOfUrl = badPartOfUrl.substring(0, badPartOfUrl.length() - 1);
            returnMessage = returnMessage + "<br>Oder entfernen Sie den Teil '/" + badPartOfUrl + "' aus der Adresszeile" +
                    "und drücken anschließend die ENTER-Taste.";
        }
        returnMessage = returnMessage + "<br/>Vielen Dank und weiterhin viel Surf-Vergnügen!" +
                "<br><br>Die Fehlermeldung war: " + ex.getMessage() + "</body></html>";

        return returnMessage;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(Exception ex) {
        return new ErrorMessage("A not covered error occurred: " + ex.getMessage(), Instant.now());
    }
}
