package de.gregorstallmeister.pg.backend.auth;

import de.gregorstallmeister.pg.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @GetMapping("/me")
    public AppUser getMe(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return null;
        }

        if (oAuth2User instanceof AppUser appUserInSession) {
            AppUser appUserInDatabase;
            try {
                appUserInDatabase = appUserService.findUserById(appUserInSession.getId());
                appUserInSession.setFavoritePicturesIds(appUserInDatabase.getFavoritePicturesIds());
            }
            catch (NoSuchElementException e) {
                throw new CustomAuthenticationException("Error while loading user from database!");
            }

            return appUserInSession;
        }
        else {
            throw new CustomAuthenticationException("logged in user is no AppUser!");
        }
    }
}
