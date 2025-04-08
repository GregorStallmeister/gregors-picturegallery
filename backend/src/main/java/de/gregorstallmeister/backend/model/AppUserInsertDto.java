package de.gregorstallmeister.backend.model;

import java.util.List;

public record AppUserInsertDto(
        String username,
        AppUserRoles role,
        List<String> favoritePicturesIds
) {
}
