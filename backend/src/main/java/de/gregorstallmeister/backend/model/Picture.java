package de.gregorstallmeister.model;

import java.time.ZonedDateTime;

public record Picture (
        String id,
        String imagePath,
        String location,
        ZonedDateTime zonedDateTime
) {
    }
