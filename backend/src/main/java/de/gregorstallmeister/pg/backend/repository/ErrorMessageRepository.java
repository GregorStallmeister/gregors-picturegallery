package de.gregorstallmeister.pg.backend.repository;

import de.gregorstallmeister.pg.backend.helpers.ErrorMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends MongoRepository <ErrorMessage, String> {
}
