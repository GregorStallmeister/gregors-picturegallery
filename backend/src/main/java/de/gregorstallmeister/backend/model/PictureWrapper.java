package de.gregorstallmeister.backend.model;

public class PictureWrapper {

    private PictureWrapper() {}

    public static PictureGetDto wrapPictureForGet (Picture picture) {

        return new PictureGetDto(
                picture.id(),
                picture.imagePath(),
                picture.location(),
                picture.instant());
    }
}
