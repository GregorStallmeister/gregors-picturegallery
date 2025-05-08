package de.gregorstallmeister.pg.backend.repository;

import de.gregorstallmeister.pg.backend.model.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends MongoRepository <Picture, String> {
}
