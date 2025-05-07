package de.gregorstallmeister.pg.backend.helpers;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
