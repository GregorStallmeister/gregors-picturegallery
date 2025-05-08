package de.gregorstallmeister.pg.backend.test.service;

import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.model.AppUserUpdateFavoritesDto;
import de.gregorstallmeister.pg.backend.model.AppUserRoles;
import de.gregorstallmeister.pg.backend.repository.AppUserRepository;
import de.gregorstallmeister.pg.backend.service.AppUserService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {

    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final AppUserService appUserService = new AppUserService(appUserRepository);

    @Test
    void updateUserFavorites() {
        // given
        String id = "123456";
        String username = "test-name";
        AppUserRoles appUserRoles = AppUserRoles.USER;
        List<String> favoritesPicturesIds = List.of("test123");
        AppUser appUser = new AppUser(id, username, appUserRoles, favoritesPicturesIds);
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        List<String> favoritesPicturesIdsUpdated = List.of("test123", "test124");
        AppUserUpdateFavoritesDto appUserUpdateFavoritesDto = new AppUserUpdateFavoritesDto(id, favoritesPicturesIdsUpdated);

        // when
        AppUser appUserUpdated = appUserService.updateUserFavorites(appUserUpdateFavoritesDto, id);

        // then
        assertEquals(id, appUserUpdated.getId());
        assertEquals(username, appUserUpdated.getUsername());
        assertEquals(appUserRoles, appUserUpdated.getRole());
        assertEquals(favoritesPicturesIdsUpdated, appUserUpdated.getFavoritePicturesIds());
    }

    @Test
    void updateUserFavoritesWhenNotPresent() {
        // given
        String id = "123456";
        List<String> favoritesPicturesIdsUpdated = List.of("test123", "test124");
        AppUserUpdateFavoritesDto appUserUpdateFavoritesDto = new AppUserUpdateFavoritesDto(id, favoritesPicturesIdsUpdated);

        // when + then
        try {
            AppUser appUserUpdated = appUserService.updateUserFavorites(appUserUpdateFavoritesDto, id);
            System.out.println(appUserUpdated);
        }
        catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            verify(appUserRepository).findById(id);
        }
    }

    @Test
    void findUserById() {
        // given
        String id = "123456";
        String username = "test-name";
        AppUserRoles appUserRoles = AppUserRoles.USER;
        List<String> favoritesPicturesIds = List.of("test123");
        AppUser appUser = new AppUser(id, username, appUserRoles, favoritesPicturesIds);
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));

        // when
        AppUser appUserFound = appUserService.findUserById(id);

        // then
        assertNotNull(appUserFound);
        assertEquals(appUser, appUserFound);
    }

    @Test
    void findUserByIdWhenNotPresent() {
        // given
        String id = "123456";

        // when + then
        try {
            AppUser appUserFound = appUserService.findUserById(id);
            System.out.println(appUserFound);
        }
        catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            verify(appUserRepository).findById(id);
        }
    }
}
