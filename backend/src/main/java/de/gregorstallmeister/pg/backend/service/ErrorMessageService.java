package de.gregorstallmeister.pg.backend.service;

import de.gregorstallmeister.pg.backend.helpers.ErrorMessage;
import de.gregorstallmeister.pg.backend.repository.ErrorMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ErrorMessageService {

    private final ErrorMessageRepository errorMessageRepository;

    public ErrorMessage insertErrorMessage(ErrorMessage errorMessage) {
        return errorMessageRepository.insert(errorMessage);
    }
}
