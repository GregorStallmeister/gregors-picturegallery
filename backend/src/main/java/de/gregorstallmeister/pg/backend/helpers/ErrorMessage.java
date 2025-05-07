package de.gregorstallmeister.pg.backend.helpers;

import java.time.Instant;

public record ErrorMessage(
        String id,
        String message,
        Instant instant
) {
}
