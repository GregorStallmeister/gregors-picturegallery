package de.gregorstallmeister.backend.auth;

import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public String getMe(@AuthenticationPrincipal OAuth2User user) {
        if (user == null) {
            throw new CustomAuthenticationException("User is not logged in!");
        }

        return user.getAttributes().get("login").toString();
    }
}
