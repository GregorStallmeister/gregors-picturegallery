package de.gregorstallmeister.backend.repository;

import de.gregorstallmeister.backend.model.Picture;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends MongoRepository <Picture, String> {
    @NotNull
    @Override
    Optional<Picture> findById(@NotNull String id);
}
