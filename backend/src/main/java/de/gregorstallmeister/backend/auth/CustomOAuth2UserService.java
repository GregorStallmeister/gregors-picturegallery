package de.gregorstallmeister.backend.auth;

import de.gregorstallmeister.backend.model.AppUser;
import de.gregorstallmeister.backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository appUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        return appUserRepository.findById(oAuth2User.getName()) /* getName() gives the ID of the GitHub user */
                .orElseGet(() -> saveUser(oAuth2User));
    }

    private AppUser saveUser(OAuth2User oAuth2User) {
        return appUserRepository.save(AppUser.builder()
                .id(oAuth2User.getName())
                .username(oAuth2User.getAttributes().get("login").toString())
                .favoritePicturesIds(new ArrayList<>())
                .build()
        );
    }
}
