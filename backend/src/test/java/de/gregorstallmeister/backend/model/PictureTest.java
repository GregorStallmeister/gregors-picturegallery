package de.gregorstallmeister.backend.model;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PictureTest {

    @Test
    void constructorTest() {
        // given
        IdService idService = new IdService();
        String id = idService.generateRandomId();
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        // when
        Picture picture = new Picture(id,imagePath, location, zonedDateTime);

        // then
        assertEquals(id, picture.id());
        assertEquals(imagePath, picture.imagePath());
        assertEquals(location, picture.location());
        assertEquals(zonedDateTime, picture.zonedDateTime());
    }
}
