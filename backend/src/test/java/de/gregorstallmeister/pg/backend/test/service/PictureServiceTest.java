package de.gregorstallmeister.pg.backend.test.service;

import de.gregorstallmeister.pg.backend.helpers.IdService;
import de.gregorstallmeister.backend.model.*;
import de.gregorstallmeister.pg.backend.model.Picture;
import de.gregorstallmeister.pg.backend.model.PictureInsertDto;
import de.gregorstallmeister.pg.backend.repository.PictureRepository;
import de.gregorstallmeister.pg.backend.service.PictureService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PictureServiceTest {

    private final PictureRepository pictureRepository = mock(PictureRepository.class);
    private final PictureService pictureService = new PictureService(pictureRepository);

    @Test
    void insertPicture() {
        // given
        String id = "test1234";
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        Instant instant = Instant.now();
        String positionInGrid = "latitude=53.7474&longitude=7.4926";
        Picture pictureToInsert = new Picture(id, imagePath, location, instant, positionInGrid);
        PictureInsertDto pictureToInsertDto = new PictureInsertDto(imagePath, location, instant, positionInGrid);
        when(pictureRepository.insert(pictureToInsert)).thenReturn(pictureToInsert);

        // when
        // Mocking IdService, see https://www.baeldung.com/mockito-mock-static-methods, section 4.
        try (MockedStatic<IdService> idServiceMockedStatic = Mockito.mockStatic(IdService.class)) {
            idServiceMockedStatic.when(IdService::generateRandomId).thenReturn(id);
            Picture pictureInserted = pictureService.insertPicture(pictureToInsertDto);

            // then
            verify(pictureRepository).insert(pictureInserted);
            assertNotNull(pictureInserted);
            assertEquals(imagePath, pictureInserted.imagePath());
            assertEquals(location, pictureInserted.location());
            assertEquals(instant, pictureInserted.instant());
            assertEquals(id, pictureInserted.id());
        }
    }

    @Test
    void getPictures() {
        // given
        Picture picture1 = new Picture(
                IdService.generateRandomId(),
                "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg",
                "Langeoog",
                Instant.parse("2025-03-26T09:17:30+01:00"),
                "latitude=53.7474&longitude=7.4926");
        Picture picture2 = new Picture(
                IdService.generateRandomId(),
                "https://gregorstallmeister.de/fotogalerie/bilder/test124.jpg",
                "Carolinensiel",
                Instant.parse("2025-08-26T09:17:30+02:00"),
                "latitude=53.6922&longitude=7.8025");
        List<Picture> expected = List.of(picture1, picture2);
        when(pictureRepository.findAll()).thenReturn(List.of(picture1, picture2));

        // when
        List<Picture> actual = pictureService.getPictures();

        // then
        verify(pictureRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getPicturesWhenNoneIsPresent() {
        // given
        List<Picture> expected = List.of();

        // when
        List<Picture> actual = pictureService.getPictures();

        // then
        verify(pictureRepository).findAll();
        assertEquals(expected, actual);
    }

    @Test
    void getPictureById() {
        // given
        String id = IdService.generateRandomId();
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        Instant instant = Instant.now();
        String positionInGrid = "latitude=53.7474&longitude=7.4926";
        Picture pictureToFind = new Picture(id, imagePath, location, instant, positionInGrid);
        when(pictureRepository.findById(id)).thenReturn(Optional.of(pictureToFind));

        // when
        Optional<Picture> optionalPicture = pictureService.getPictureById(id);

        // then
        verify(pictureRepository).findById(id);
        assertNotNull(optionalPicture);
        assertTrue(optionalPicture.isPresent());
        assertEquals(pictureToFind, optionalPicture.get());
    }

    @Test
    void getPictureByIdWhenNotPresent() {
        // given
        String id = "test_not_present_1234";

        // when
        Optional<Picture> optionalPicture = pictureService.getPictureById(id);

        // then
        verify(pictureRepository).findById(id);
        assertNotNull(optionalPicture);
        assertFalse(optionalPicture.isPresent());
    }

    @Test
    void updatePicture() {
        // given
        String id = IdService.generateRandomId();
        when(pictureRepository.existsById(id)).thenReturn(true);

        String imagePathUpdated = "https://gregorstallmeister.de/fotogalerie/bilder/test123456.jpg";
        String locationUpdated = "Carolinensiel";
        Instant instantUpdated = Instant.parse("2025-08-26T09:17:30+02:00");
        String positionInGridUpdated = "latitude=53.6922&longitude=7.8025";

        Picture pictureModified = new Picture(id, imagePathUpdated, locationUpdated, instantUpdated, positionInGridUpdated);
        PictureInsertDto pictureInsertDto = new PictureInsertDto(pictureModified.imagePath(),
                pictureModified.location(), pictureModified.instant(), pictureModified.positionInGrid());

        // when
        Picture pictureUpdated = pictureService.updatePicture(pictureInsertDto, id);

        // then
        verify(pictureRepository).save(pictureModified);
        assertNotNull(pictureUpdated);
        assertEquals(pictureModified, pictureUpdated);
    }

    @Test
    void updatePictureWhenNotPresent() {
        // given
        String id = IdService.generateRandomId();
        String imagePath = "https://gregorstallmeister.de/fotogalerie/bilder/test123.jpg";
        String location = "Langeoog";
        Instant instant = Instant.now();
        String positionInGrid = "latitude=53.7474&longitude=7.4926";
        PictureInsertDto pictureInsertDto = new PictureInsertDto(imagePath, location, instant, positionInGrid);

        // when + then
        assertThrows(NoSuchElementException.class, () -> pictureService.updatePicture(pictureInsertDto, id));
        verify(pictureRepository).existsById(id);
    }

    @Test
    void deletePicture() {
        // given
        String id = IdService.generateRandomId();
        when(pictureRepository.existsById(id)).thenReturn(true);

        // when
        pictureService.deletePicture(id);

        // then
        verify(pictureRepository).deleteById(id);
        assertEquals(0, pictureRepository.count());
    }

    @Test
    void deletePictureWhenNotPresent() {
        // given: No picture; an ID only
        String id = "test1234";

        // when + then
        assertThrows(NoSuchElementException.class, () -> pictureService.deletePicture(id));
    }
}
