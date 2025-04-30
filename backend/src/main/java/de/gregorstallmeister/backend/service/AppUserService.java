package de.gregorstallmeister.backend.service;

import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserUpdateFavoritesDto;
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

    public AppUser updateUserFavorites(@NotNull AppUserUpdateFavoritesDto appUserUpdateFavoritesDto, String id) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(id);

        if (appUserOptional.isEmpty()) {
            throw new NoSuchElementException("User to update with id " + id + " is not present in database!");
        }

        AppUser appUserUpdated = new AppUser(id, appUserOptional.get().getUsername(), appUserOptional.get().getRole(),
                appUserUpdateFavoritesDto.favoritePicturesIds());
        appUserRepository.save(appUserUpdated);
        return appUserUpdated;
    }

    public AppUser findUserById(String id) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isPresent()) {
            return optionalAppUser.get();
        }

        throw new NoSuchElementException("User with id " + id + " was not found!");
    }
}
