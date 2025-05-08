package de.gregorstallmeister.pg.backend.controller;

import de.gregorstallmeister.pg.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.pg.backend.helpers.ErrorMessage;
import de.gregorstallmeister.pg.backend.helpers.IdService;
import de.gregorstallmeister.pg.backend.service.ErrorMessageService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorMessageService errorMessageService;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorMessage handleNoSuchElementException(@NotNull NoSuchElementException ex) {
        ErrorMessage errorMessage = new ErrorMessage(IdService.generateRandomId(), "An error occurred: " + ex.getMessage(), Instant.now());
        errorMessageService.insertErrorMessage(errorMessage);
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomAuthenticationException.class)
    public ErrorMessage handleCustomAuthenticationException(@NotNull CustomAuthenticationException ex) {
        ErrorMessage errorMessage = new ErrorMessage(IdService.generateRandomId(), "An error occurred: " + ex.getMessage(), Instant.now());
        errorMessageService.insertErrorMessage(errorMessage);
        return errorMessage;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorMessage handleException(@NotNull Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(IdService.generateRandomId(), "A not covered error occurred: " + ex.getMessage(), Instant.now());
        errorMessageService.insertErrorMessage(errorMessage);
        return errorMessage;
    }
}
