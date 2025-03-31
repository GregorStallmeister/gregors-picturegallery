package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.model.IdService;
import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public Picture insertPicture(@NotNull PictureInsertDto pictureInsertDto) {
        IdService idService = new IdService();
        Picture pictureToInsert = new Picture(
                idService.generateRandomId(),
                pictureInsertDto.imagePath(),
                pictureInsertDto.location(),
                pictureInsertDto.instant());

        pictureRepository.insert(pictureToInsert);

        return pictureToInsert;
    }

    public List<Picture> getPictures() {
        return pictureRepository.findAll();
    }

    public Optional<Picture> getPictureById(String id) {
        return pictureRepository.findById(id);
    }

    public Picture updatePicture(@NotNull PictureInsertDto pictureInsertDto, String id) throws NoSuchElementException {
        Optional<Picture> optionalPicture = pictureRepository.findById(id);

        if (optionalPicture.isPresent()) {
            Picture pictureUpdated = new Picture(id, pictureInsertDto.imagePath(), pictureInsertDto.location(), pictureInsertDto.instant());
            pictureRepository.save(pictureUpdated);
            return pictureUpdated;
                } else {
            throw new  NoSuchElementException("Picture not found with ID: " + id);
        }
    }
}
