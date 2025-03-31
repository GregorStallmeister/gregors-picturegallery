package de.gregorstallmeister.backend.helpers;

import java.time.Instant;

public record ErrorMessage(
        String message,
        Instant instant
) {
}
