package de.gregorstallmeister.pg.backend.test.auth;

import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.model.AppUserRoles;
import de.gregorstallmeister.pg.backend.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    @DirtiesContext
    void getMe() {
        // given: nothing but the class members
        String id = "123456";
        AppUser appUser = new AppUser(id, "test-name", AppUserRoles.USER);
        appUserRepository.save(appUser);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me").with(oauth2Login().oauth2User(appUser)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                               "id": "123456",
                               "username": "test-name",
                               "role": "USER"
                            }
                            """));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getMeOicdUser() {
        // given: nothing but the class members

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me").with(oidcLogin().userInfoToken(token ->
                            token.claim("login", "testUser"))))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: logged in user is no AppUser!"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getMeUserNotInDatabase() {
        // given: nothing but the class members
        String id = "123456";
        AppUser appUser = new AppUser(id, "test-name", AppUserRoles.USER);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me").with(oauth2Login().oauth2User(appUser)))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().json("""
                            {
                              "message": "An error occurred: Error while loading user from database!"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void getMeWhenNoUserLoggedIn() {
        // given: nothing but the class members

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/auth/me"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(""));
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
