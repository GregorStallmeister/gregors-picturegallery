package de.gregorstallmeister.pg.backend.test.helpers;

import de.gregorstallmeister.pg.backend.helpers.ErrorMessage;
import de.gregorstallmeister.pg.backend.helpers.IdService;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorMessageTest {

    @Test
    void constructorTest() {
        // given
        String id = IdService.generateRandomId();
        String message = "test1234";
        Instant instant = Instant.now();

        // when
        ErrorMessage errorMessage = new ErrorMessage(id, message, instant);

        // then
        assertEquals(id, errorMessage.id());
        assertEquals(message, errorMessage.message());
        assertEquals(instant, errorMessage.instant());
    }
}
