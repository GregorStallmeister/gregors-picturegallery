package de.gregorstallmeister.backend.model;

import java.time.ZonedDateTime;

public record PictureInsertDto(
        String imagePath,
        String location,
        ZonedDateTime zonedDateTime
) {
    }
