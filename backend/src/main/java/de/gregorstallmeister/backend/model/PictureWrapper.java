package de.gregorstallmeister.backend.model;

import java.util.List;

public class PictureWrapper {

    private PictureWrapper() {}

    public static PictureGetDto wrapPictureForGet (Picture picture) {
        return new PictureGetDto(
                picture.id(),
                picture.imagePath(),
                picture.location(),
                picture.instant());
    }

    public static List<PictureGetDto> wrapPicturesForGet(List<Picture> pictures) {
        return pictures.stream().map(picture -> new PictureGetDto(
                picture.id(), picture.imagePath(), picture.location(), picture.instant()))
                .toList();
    }
}
