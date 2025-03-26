package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.model.IdService;
import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.repository.PictureRepository;
import de.gregorstallmeister.backend.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class PictureServiceTest {

    @Autowired
    private MockMvc mockMvc;

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
}
