package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserInsertDto;
import de.gregorstallmeister.backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUser updateUser(@NotNull AppUserInsertDto appUserInsertDto, String id) throws NoSuchElementException {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);

        if (appUserOptional.isPresent()) {
            AppUser appUserUpdated = new AppUser(id, appUserInsertDto.username(), appUserInsertDto.role(), appUserInsertDto.favoritePicturesIds());
            appUserRepository.save(appUserUpdated);
            return appUserUpdated;
        }
        else {
            throw new NoSuchElementException("User to update is not present in database!");
        }
    }

    public AppUser findUserById(String id) throws NoSuchElementException {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isPresent()) {
            return optionalAppUser.get();
        }

        throw new NoSuchElementException("User with id " + id + " was not found!");
    }
}
