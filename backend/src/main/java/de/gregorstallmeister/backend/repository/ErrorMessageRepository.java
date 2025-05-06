package de.gregorstallmeister.backend.repository;

import de.gregorstallmeister.backend.helpers.ErrorMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorMessageRepository extends MongoRepository <ErrorMessage, String> {
}
