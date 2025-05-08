package de.gregorstallmeister.pg.backend.controller;

import de.gregorstallmeister.pg.backend.helpers.AppUserWrapper;
import de.gregorstallmeister.pg.backend.helpers.CustomAuthenticationException;
import de.gregorstallmeister.pg.backend.helpers.IAuthenticationFacade;
import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.model.AppUserGetDto;
import de.gregorstallmeister.pg.backend.model.AppUserUpdateFavoritesDto;
import de.gregorstallmeister.pg.backend.service.AppUserService;
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
            throw new CustomAuthenticationException("ID of logged in user to update their favorites is not the ID specified!");
        }

        return AppUserWrapper.wrapUserForGet(appUserService.updateUserFavorites(appUserUpdateFavoritesDto, id));
    }
}
