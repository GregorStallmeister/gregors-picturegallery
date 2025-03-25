package de.gregorstallmeister.backend.model;

import java.time.ZonedDateTime;

public record PictureGetDto(
        String id,
        String imagePath,
        String location,
        ZonedDateTime zonedDateTime
) {
    }
