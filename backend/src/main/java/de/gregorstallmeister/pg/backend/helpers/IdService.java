package de.gregorstallmeister.pg.backend.helpers;

import java.util.UUID;

public class IdService {
    public static String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    private IdService() {}
}
