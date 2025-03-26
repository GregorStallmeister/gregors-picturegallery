package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.model.IdService;
import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.repository.PictureRepository;
import de.gregorstallmeister.backend.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PictureServiceTest {

    private PictureRepository pictureRepository;
    private PictureService pictureService;

    @BeforeEach
    void setup() {
        pictureRepository = mock(PictureRepository.class);
        pictureService = new PictureService(pictureRepository);
    }

    @Test
    void postPicture() {
        // given
        IdService idService = new IdService();
        String id = idService.generateRandomId();
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        Instant instant = Instant.now();
        Picture pictureToInsert = new Picture(id, imagePath, location, instant);
        PictureInsertDto pictureToInsertDto = new PictureInsertDto(imagePath, location, instant);
        when(pictureRepository.insert(pictureToInsert)).thenReturn(pictureToInsert);

        // when
        Picture pictureInserted = pictureService.insertPicture(pictureToInsertDto);

        // then
        verify(pictureRepository).insert(pictureInserted);
        assertNotNull(pictureInserted);
        assertEquals(imagePath, pictureInserted.imagePath());
        assertEquals(location, pictureInserted.location());
        assertEquals(instant, pictureInserted.instant());
        assertNotNull(pictureInserted.id());
    }

    @Test
    void giveAllPictures() {
        // given
        IdService idService = new IdService();
        Picture picture1 = new Picture(
                idService.generateRandomId(),
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.now());
        Picture picture2 = new Picture(
                idService.generateRandomId(),
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.now());
        List<Picture> expected = List.of(picture1, picture2);
        when(pictureRepository.findAll()).thenReturn(List.of(picture1, picture2));

        // when
        List<Picture> actual = pictureService.giveAllPictures();

        // then
        verify(pictureRepository).findAll();
        assertEquals(expected, actual);
    }
}
