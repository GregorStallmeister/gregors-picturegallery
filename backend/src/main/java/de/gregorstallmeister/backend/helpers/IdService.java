package de.gregorstallmeister.backend.helpers;

import java.util.UUID;

public class IdService {
    public String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
