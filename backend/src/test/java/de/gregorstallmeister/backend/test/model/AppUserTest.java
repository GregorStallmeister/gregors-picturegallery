package de.gregorstallmeister.backend.test.model;

import de.gregorstallmeister.backend.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(id, appUser.getName());
        assertEquals(username, appUser.getUsername());
        assertEquals(role, appUser.getRole());
    }

    @Test
    void constructorNoArgsTest() {
        // given: no args

        // when
        AppUser appUser = new AppUser();

        // then
        assertNull(appUser.getId());
        assertNull(appUser.getName());
        assertNull(appUser.getRole());
        assertNull(appUser.getAttributes());
        assertNull(appUser.getSimpleGrantedAuthorities());
    }

    @Test
    void constructorTestAllArgs() {
        // given
        String id = "test-id-1234";
        String username = "test-name";
        String role = "test-role";
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("test-id", "test-value");
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ADMIN"));

        // when
        AppUser appUser = new AppUser(id, username, role, attributes, simpleGrantedAuthorityList);

        // then
        assertEquals(id, appUser.getId());
        assertEquals(id, appUser.getName());
        assertEquals(username, appUser.getUsername());
        assertEquals(role, appUser.getRole());
        assertEquals(attributes, appUser.getAttributes());
        assertEquals(simpleGrantedAuthorityList, appUser.getAuthorities());
    }
}
