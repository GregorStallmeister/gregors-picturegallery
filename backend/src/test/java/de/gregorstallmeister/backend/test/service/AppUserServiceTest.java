package de.gregorstallmeister.backend.test.service;

import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.model.AppUserInsertDto;
import de.gregorstallmeister.backend.model.AppUserRoles;
import de.gregorstallmeister.backend.repository.AppUserRepository;
import de.gregorstallmeister.backend.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserServiceTest {

    private AppUserRepository appUserRepository;
    private AppUserService appUserService;

    @BeforeEach
    void setup() {
        appUserRepository = mock(AppUserRepository.class);
        appUserService = new AppUserService(appUserRepository);
    }

    @Test
    void updateUser() {
        // given
        String id = "123456";
        String username = "test-name";
        AppUserRoles appUserRoles = AppUserRoles.USER;
        List<String> favoritesPicturesIds = List.of("test123");
        AppUser appUser = new AppUser(id, username, appUserRoles, favoritesPicturesIds);
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        List<String> favoritesPicturesIdsUpdated = List.of("test123", "test124");
        AppUserInsertDto appUserInsertDto = new AppUserInsertDto(username, appUserRoles, favoritesPicturesIdsUpdated);

        // when
        AppUser appUserUpdated = appUserService.updateUser(appUserInsertDto, id);

        // then
        assertEquals(id, appUserUpdated.getId());
        assertEquals(username, appUserUpdated.getUsername());
        assertEquals(appUserRoles, appUserUpdated.getRole());
        assertEquals(favoritesPicturesIdsUpdated, appUserUpdated.getFavoritePicturesIds());
    }

    @Test
    void updateUserWhenNotPresent() {
        // given
        String id = "123456";
        String username = "test-name";
        AppUserRoles appUserRoles = AppUserRoles.USER;
        List<String> favoritesPicturesIdsUpdated = List.of("test123", "test124");
        AppUserInsertDto appUserInsertDto = new AppUserInsertDto(username, appUserRoles, favoritesPicturesIdsUpdated);

        // when + then
        try {
            AppUser appUserUpdated = appUserService.updateUser(appUserInsertDto, id);
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
