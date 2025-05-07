package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.helpers.ErrorMessage;
import de.gregorstallmeister.backend.repository.ErrorMessageRepository;
import de.gregorstallmeister.backend.service.ErrorMessageService;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ErrorMessageServiceTest {

    private final ErrorMessageRepository errorMessageRepository = mock(ErrorMessageRepository.class);
    private final ErrorMessageService errorMessageService = new ErrorMessageService(errorMessageRepository);

    @Test
    void insertErrorMessage() {
        // given
        String id = "test1234";
        String message = "test-message";
        Instant instant = Instant.now();
        ErrorMessage errorMessage = new ErrorMessage(id, message, instant);
        when(errorMessageRepository.insert(errorMessage)).thenReturn(errorMessage);

        // when
        ErrorMessage errorMessageInserted = errorMessageService.insertErrorMessage(errorMessage);

        // then
        verify(errorMessageRepository).insert(errorMessage);
        assertEquals(id, errorMessageInserted.id());
        assertEquals(message, errorMessageInserted.message());
        assertEquals(instant, errorMessageInserted.instant());
    }
}
