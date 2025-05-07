package de.gregorstallmeister.pg.backend.test.helpers;

import de.gregorstallmeister.pg.backend.helpers.CustomAuthenticationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomAuthenticationExceptionTest {

    @Test
    void constructorTest() {
        // given
        String message = "Test1234";

        // when
        CustomAuthenticationException customAuthenticationException = new CustomAuthenticationException(message);

        // then
        assertEquals(message, customAuthenticationException.getMessage());
    }
}
