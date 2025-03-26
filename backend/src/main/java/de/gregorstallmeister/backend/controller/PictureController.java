package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureGetDto;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.model.PictureWrapper;
import de.gregorstallmeister.backend.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/picture")
    @ResponseStatus(HttpStatus.CREATED) // 201 - Standard, when something was created
    public PictureGetDto postPicture(@RequestBody PictureInsertDto pictureInsertDto) {
        Picture pictureInserted = pictureService.insertPicture(pictureInsertDto);

        return PictureWrapper.wrapPictureForGet(pictureInserted);
    }
}
