package de.gregorstallmeister.backend.model;

import java.time.Instant;

public record PictureInsertDto(
        String imagePath,
        String location,
        Instant instant
) {
    }
