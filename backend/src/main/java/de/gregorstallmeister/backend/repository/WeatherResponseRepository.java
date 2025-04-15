package de.gregorstallmeister.backend.repository;

import de.gregorstallmeister.backend.model.weather.WeatherResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherResponseRepository extends MongoRepository<WeatherResponse, String> {
}
