package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.model.IdService;
import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;

    public Picture insertPicture(PictureInsertDto pictureInsertDto) {
        IdService idService = new IdService();
        Picture pictureToInsert = new Picture(
                idService.generateRandomId(),
                pictureInsertDto.imagePath(),
                pictureInsertDto.location(),
                pictureInsertDto.instant());

        pictureRepository.insert(pictureToInsert);

        return pictureToInsert;
    }
}
