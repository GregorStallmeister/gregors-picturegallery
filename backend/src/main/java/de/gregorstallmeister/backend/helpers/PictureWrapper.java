package de.gregorstallmeister.backend.helpers;

import de.gregorstallmeister.backend.model.Picture;
import de.gregorstallmeister.backend.model.PictureGetDto;

import java.util.List;

public class PictureWrapper {

    private PictureWrapper() {
    }

    public static PictureGetDto wrapPictureForGet(Picture picture) {
        return new PictureGetDto(
                picture.id(),
                picture.imagePath(),
                picture.location(),
                picture.instant(),
                picture.positionInGrid());
    }

    public static List<PictureGetDto> wrapPicturesForGet(List<Picture> pictures) {
        return pictures.stream().map(picture -> new PictureGetDto(
                        picture.id(), picture.imagePath(), picture.location(), picture.instant(), picture.positionInGrid()))
                .toList();
    }
}
