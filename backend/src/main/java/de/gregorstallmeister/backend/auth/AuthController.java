package de.gregorstallmeister.backend.auth;

import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.backend.model.AppUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public AppUser getMe(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new CustomAuthenticationException("User is not logged in!");
        }

        if (oAuth2User instanceof AppUser appUser) {
            return appUser;
        }
        else {
            return null;
        }
    }
}
