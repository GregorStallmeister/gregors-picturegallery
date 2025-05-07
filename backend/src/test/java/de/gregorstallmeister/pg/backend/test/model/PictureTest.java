package de.gregorstallmeister.pg.backend.test.model;

import de.gregorstallmeister.pg.backend.helpers.IdService;
import de.gregorstallmeister.pg.backend.model.Picture;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PictureTest {

    @Test
    void constructorTest() {
        // given
        String id = IdService.generateRandomId();
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        Instant instant = Instant.now();
        String positionInGrid = "latitude=53.7474&longitude=7.4926";

        // when
        Picture picture = new Picture(id, imagePath, location, instant, positionInGrid);

        // then
        assertEquals(id, picture.id());
        assertEquals(imagePath, picture.imagePath());
        assertEquals(location, picture.location());
        assertEquals(instant, picture.instant());
        assertEquals(positionInGrid, picture.positionInGrid());
    }
}
