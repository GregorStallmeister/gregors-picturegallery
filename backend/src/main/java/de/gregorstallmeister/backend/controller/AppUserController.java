package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.helpers.AppUserWrapper;
import de.gregorstallmeister.backend.model.AppUserGetDto;
import de.gregorstallmeister.backend.model.AppUserInsertDto;
import de.gregorstallmeister.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppUserGetDto updateUser (AppUserInsertDto appUserInsertDto, @PathVariable String id) {
        return AppUserWrapper.wrapUserForGet(appUserService.updateUser(appUserInsertDto, id));
    }
}
