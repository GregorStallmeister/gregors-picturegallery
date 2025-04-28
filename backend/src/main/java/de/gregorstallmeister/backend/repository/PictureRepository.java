package de.gregorstallmeister.backend.repository;

import de.gregorstallmeister.backend.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends MongoRepository <Picture, String> {
}
