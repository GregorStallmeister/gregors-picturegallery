package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.backend.helpers.ErrorMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.NoSuchElementException;

import static java.lang.System.getenv;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleNoSuchElementException(@NotNull NoSuchElementException ex) {
        return new ErrorMessage("An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomAuthenticationException.class)
    public ErrorMessage handleCustomAuthenticationException(@NotNull CustomAuthenticationException ex) {
        return new ErrorMessage("An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(@NotNull NoResourceFoundException ex) {
        return "<html><head><title>Gregors Fotogalerie - Fehlerseite</title>" +
                "<meta http-equiv=\"Refresh\" content=\"5;URL=\"" + getenv("APP_URL") + "\"/></head>" +
                "<body style=\"background-color:#F6F2B4;\">Gregors Fotogalerie - schön, dass Sie da sind und herzlich willkommen!" +
                "<br><br>Diese Seite ist leider nicht an allen Stellen kompatibel zum Neu-Laden per Browser-Button." +
                "Sie werden in 5 Sekunden automatisch zur Startseite weitergeleitet. Sollte dies nicht geschehen, " +
                "klicken Sie bitte <a href=\"" + getenv("APP_URL") + "\">hier</a>." +
                "<br/>Vielen Dank und weiterhin viel Surf-Vergnügen!" +
                "<br><br>Die Fehlermeldung war: " + ex.getMessage() + "</body></html>";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(@NotNull Exception ex) {
        return new ErrorMessage("A not covered error occurred: " + ex.getMessage(), Instant.now());
    }
}
