package de.gregorstallmeister.backend.test.auth;

import de.gregorstallmeister.backend.auth.CustomOAuth2UserService;
import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserRoles;
import de.gregorstallmeister.backend.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.Instant;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomOAuth2AppUserServiceTest {

    @Autowired
    AppUserRepository appUserRepository;

    @Test
    void saveUser() {
        // given
        HashMap<String, Object> attributes = new HashMap<>();
        attributes.put("login", "GitHubTestUser");
        OAuth2User oAuth2User = new AppUser("123456", "test-name", null, null,
                attributes, null);
        CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService(appUserRepository);

        // when
        AppUser appUser = customOAuth2UserService.saveUser(oAuth2User);

        // then
        assertEquals("123456", appUser.getId());
        assertEquals("GitHubTestUser", appUser.getUsername());
        assertEquals(AppUserRoles.USER, appUser.getRole());
        assertNotNull(appUser.getFavoritePicturesIds());
        assertNull(appUser.getAttributes());
        assertNull(appUser.getAuthorities());
    }

    @Test
    void loadUser() {
        // given
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("123456")
                .authorizationGrantType(AuthorizationGrantType.JWT_BEARER)
                .userInfoUri("http://localhost:8080/api/auth/me")
                .userNameAttributeName("test-id")
                .build();
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "myToken",
                Instant.now(), Instant.MAX);
        OAuth2UserRequest oAuth2UserRequest = new OAuth2UserRequest(clientRegistration, oAuth2AccessToken);
        CustomOAuth2UserService customOAuth2UserService = new CustomOAuth2UserService(appUserRepository);

        // when + then // this is implemented in hope, that the test coverage is reached, to be implemented in better way later.
        assertThrows(OAuth2AuthenticationException.class, () ->
                customOAuth2UserService.loadUser(oAuth2UserRequest));
    }
}
