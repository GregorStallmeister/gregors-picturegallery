package de.gregorstallmeister.pg.backend.helpers;

import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.model.AppUserGetDto;

public class AppUserWrapper {

    private AppUserWrapper() {

    }

    public static AppUserGetDto wrapUserForGet(AppUser appUser) {
        return new AppUserGetDto(appUser.getId(), appUser.getUsername(), appUser.getRole(), appUser.getFavoritePicturesIds());
    }
}
