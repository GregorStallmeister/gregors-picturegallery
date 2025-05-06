package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.backend.helpers.ErrorMessage;
import de.gregorstallmeister.backend.helpers.IdService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleNoSuchElementException(@NotNull NoSuchElementException ex) {
        return new ErrorMessage(IdService.generateRandomId(), "An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomAuthenticationException.class)
    public ErrorMessage handleCustomAuthenticationException(@NotNull CustomAuthenticationException ex) {
        return new ErrorMessage(IdService.generateRandomId(), "An error occurred: " + ex.getMessage(), Instant.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(@NotNull Exception ex) {
        return new ErrorMessage(IdService.generateRandomId(), "A not covered error occurred: " + ex.getMessage(), Instant.now());
    }
}
