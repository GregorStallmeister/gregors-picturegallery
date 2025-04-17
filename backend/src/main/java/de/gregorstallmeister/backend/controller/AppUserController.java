package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.AppUserWrapper;
import de.gregorstallmeister.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.backend.helpers.IAuthenticationFacade;
import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserGetDto;
import de.gregorstallmeister.backend.model.AppUserUpdateFavoritesDto;
import de.gregorstallmeister.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserController {

    private IAuthenticationFacade authenticationFacade;

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(IAuthenticationFacade authenticationFacade, AppUserService appUserService) {
        this.authenticationFacade = authenticationFacade;
        this.appUserService = appUserService;
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDto updateUserFavorites(@RequestBody AppUserUpdateFavoritesDto appUserUpdateFavoritesDto, @PathVariable String id) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (!(authentication.getPrincipal() instanceof AppUser appUser)) {
            throw new CustomAuthenticationException("Logged in user to update their favorites is not an AppUser!");
        }

        if (!Objects.equals(appUser.getId(), id)) {
            throw new CustomAuthenticationException("ID of logged in user to update their favorites is not the id specified!");
        }

        return AppUserWrapper.wrapUserForGet(appUserService.updateUserFavorites(appUserUpdateFavoritesDto, id));
    }
}
