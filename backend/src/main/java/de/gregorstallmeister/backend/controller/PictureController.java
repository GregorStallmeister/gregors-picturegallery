package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureGetDto;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.model.PictureWrapper;
import de.gregorstallmeister.backend.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/picture")
    @ResponseStatus(HttpStatus.OK)  // 200 - Standard for correct response, the returned list can be empty
    public List<PictureGetDto> getAllPictures() {
        return PictureWrapper.wrapPicturesForGet(pictureService.getPictures());
    }

    @GetMapping("/picture/{id}")
    public ResponseEntity<PictureGetDto> getPictureById(@PathVariable String id) {
        Optional<Picture> optionalPicture = pictureService.getPictureById(id);

        return optionalPicture.map(picture
                -> ResponseEntity.ok(PictureWrapper.wrapPictureForGet(picture)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/picture/{id}")
    public PictureGetDto updatePicture(@RequestBody PictureInsertDto pictureInsertDto, @PathVariable String id) {
        return PictureWrapper.wrapPictureForGet(pictureService.updatePicture(pictureInsertDto, id));
    }
}
