package de.gregorstallmeister.pg.backend.model;

import java.util.List;

public record AppUserGetDto(
        String id,
        String username,
        AppUserRoles role,
        List<String>favoritePicturesIds
) {
}
