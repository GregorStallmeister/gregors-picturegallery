package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.helpers.ErrorMessage;
import de.gregorstallmeister.backend.repository.ErrorMessageRepository;
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
