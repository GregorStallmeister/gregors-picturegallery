package de.gregorstallmeister.backend.test.model;

import de.gregorstallmeister.backend.model.AppUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppUserTest {

    @Test
    void constructorTest() {
        // given
        String id = "test-id-1234";
        String username = "test-name";
        String role = "test-role";

        // when
        AppUser appUser = new AppUser(id, username, role);

        // then
        assertEquals(id, appUser.getId());
        assertEquals(username, appUser.getUsername());
        assertEquals(role, appUser.getRole());
    }
}
