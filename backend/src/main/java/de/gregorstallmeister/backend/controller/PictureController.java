package de.gregorstallmeister.backend.controller;

import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureGetDto;
import de.gregorstallmeister.backend.model.PictureInsertDto;
import de.gregorstallmeister.backend.helpers.PictureWrapper;
import de.gregorstallmeister.backend.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/picture")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // 201 - Standard, when something was created
    public PictureGetDto postPicture(@RequestBody PictureInsertDto pictureInsertDto) {
        Picture pictureInserted = pictureService.insertPicture(pictureInsertDto);

        return PictureWrapper.wrapPictureForGet(pictureInserted);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)  // 200 - Standard for correct response, the returned list can be empty
    public List<PictureGetDto> getAllPictures() {
        return PictureWrapper.wrapPicturesForGet(pictureService.getPictures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PictureGetDto> getPictureById(@PathVariable String id) {
        Optional<Picture> optionalPicture = pictureService.getPictureById(id);

        // Design decision: when no picture is found, an empty Optional is returned.
        // An empty Optional is, when no picture with the requested id exists, the result of the call to the repository.
        // And it is a normal thing that a picture does not exist with the requested id, not an exceptional case.
        // Exceptional cases, like the database is not available, will be handled automatically by the GlobalExceptionHandler.
        return optionalPicture.map(picture
                -> ResponseEntity.ok(PictureWrapper.wrapPictureForGet(picture)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PictureGetDto updatePicture(@RequestBody PictureInsertDto pictureInsertDto, @PathVariable String id) {
        return PictureWrapper.wrapPictureForGet(pictureService.updatePicture(pictureInsertDto, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // design decision: 204 - no content when successful
    public void deletePicture(@PathVariable String id) {
        pictureService.deletePicture(id);
    }
}
