package de.gregorstallmeister.backend.model;

import java.util.UUID;

public class IdService {
    public String generateRandomId() {
        return UUID.randomUUID().toString();
    }
}
