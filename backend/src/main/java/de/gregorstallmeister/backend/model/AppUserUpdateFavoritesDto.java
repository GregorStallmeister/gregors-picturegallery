package de.gregorstallmeister.backend.model;

import java.util.List;

public record AppUserUpdateFavoritesDto(
        String id,
        List<String> favoritePicturesIds
) {
}
