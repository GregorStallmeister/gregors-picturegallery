package de.gregorstallmeister.pg.backend.model;

import java.time.Instant;

public record Picture (
        String id,
        String imagePath,
        String location,
        Instant instant,
        String positionInGrid
) {
    }
