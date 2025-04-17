package de.gregorstallmeister.backend.test.controller;

import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserRoles;
import de.gregorstallmeister.backend.repository.AppUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    @DirtiesContext
    void updateUserFavorites() {
        // given
        String id = "test1234";
        List<String> favoritePicturesIds = List.of("picture-120");
        AppUser appUser = new AppUser(id, "test-user", AppUserRoles.ADMIN, favoritePicturesIds);
        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(appUser, null, id));
        appUserRepository.insert(appUser);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {"username": "test-user",
                                     "role": "ADMIN",
                                     "favoritePicturesIds":
                                       [
                                          "picture-123",
                                          "picture-124"
                                       ]
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                    {"username": "test-user",
                                     "role": "ADMIN",
                                     "favoritePicturesIds":
                                       [
                                          "picture-123",
                                          "picture-124"
                                       ]
                                    }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void updateUserFavoritesWhenNotPresent() {
        // given
        String id = "test1234";
        List<String> favoritePicturesIds = List.of("picture-120");
        AppUser appUser = new AppUser(id, "test-user", AppUserRoles.ADMIN, favoritePicturesIds);
        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(appUser, null, id));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {
                                     "username": "test-user",
                                     "role": "ADMIN",
                                     "favoritePicturesIds":
                                       [
                                          "picture-123",
                                          "picture-124"
                                       ]
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                    {
                                      "message": "An error occurred: User to update with id test1234 is not present in database!"
                                    }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void updateUserFavoritesWithDifferentIds() {
        // given
        String idLoggedInUser = "test1234";
        String idUserToUpdate = "test5678";
        List<String> favoritePicturesIds = List.of("picture-120");
        AppUser appUser = new AppUser(idLoggedInUser, "test-user", AppUserRoles.ADMIN, favoritePicturesIds);
        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(appUser, null, idLoggedInUser));
        appUserRepository.insert(appUser);

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + idUserToUpdate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {"username": "test-user",
                                     "role": "ADMIN",
                                     "favoritePicturesIds":
                                       [
                                          "picture-123",
                                          "picture-124"
                                       ]
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                    {
                                      "message": "An error occurred: ID of logged in user to update their favorites is not the ID specified!"
                                    }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    @DirtiesContext
    void updateUserFavoritesWithUserNotAnAppUser() {
        // given
        String idLoggedInUser = "test1234";
        List<String> favoritePicturesIds = List.of("picture-120");
        AppUser appUser = new AppUser(idLoggedInUser, "test-user", AppUserRoles.ADMIN, favoritePicturesIds);
        appUserRepository.insert(appUser);
        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(null, Map.of("test1234", "test"), idLoggedInUser);
        SecurityContextHolder.getContext().setAuthentication(new OAuth2AuthenticationToken(defaultOAuth2User, null, idLoggedInUser));

        // when + then
        try {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + idLoggedInUser)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""                                    
                                    {"username": "test-user",
                                     "role": "ADMIN",
                                     "favoritePicturesIds":
                                       [
                                          "picture-123",
                                          "picture-124"
                                       ]
                                    }
                                    """))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                    .andExpect(MockMvcResultMatchers.content().json("""
                                    {
                                      "message": "An error occurred: Logged in user to update their favorites is not an AppUser!"
                                    }
                            """))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.instant").isNotEmpty());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}
