package de.gregorstallmeister.pg.backend.model;

import java.util.List;

public record AppUserUpdateFavoritesDto(
        String id,
        List<String> favoritePicturesIds
) {
}
