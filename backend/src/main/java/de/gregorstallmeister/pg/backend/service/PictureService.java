package de.gregorstallmeister.pg.backend.service;

import de.gregorstallmeister.pg.backend.helpers.IdService;
import de.gregorstallmeister.pg.backend.model.Picture;
import de.gregorstallmeister.pg.backend.model.PictureInsertDto;
import de.gregorstallmeister.pg.backend.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public Picture insertPicture(@NotNull PictureInsertDto pictureInsertDto) {
        Picture pictureToInsert = new Picture(
                IdService.generateRandomId(),
                pictureInsertDto.imagePath(),
                pictureInsertDto.location(),
                pictureInsertDto.instant(),
                pictureInsertDto.positionInGrid());

        return pictureRepository.insert(pictureToInsert);
    }

    public List<Picture> getPictures() {
        return pictureRepository.findAll();
    }

    public Optional<Picture> getPictureById(String id) {
        return pictureRepository.findById(id);
    }

    public Picture updatePicture(@NotNull PictureInsertDto pictureInsertDto, String id) {
        if (pictureRepository.existsById(id)) {
            Picture pictureUpdated = new Picture(id, pictureInsertDto.imagePath(), pictureInsertDto.location(),
                    pictureInsertDto.instant(), pictureInsertDto.positionInGrid());
            pictureRepository.save(pictureUpdated);
            return pictureUpdated;
        } else {
            throw new NoSuchElementException("Picture to update was not found with ID: " + id);
        }
    }

    public void deletePicture(String id) {
        if (pictureRepository.existsById(id))
            pictureRepository.deleteById(id);
        else
            throw new NoSuchElementException("Picture to delete was not found with ID: " + id);
    }
}
