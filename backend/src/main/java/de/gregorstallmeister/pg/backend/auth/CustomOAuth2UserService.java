package de.gregorstallmeister.pg.backend.auth;

import de.gregorstallmeister.pg.backend.model.AppUser;
import de.gregorstallmeister.pg.backend.model.AppUserRoles;
import de.gregorstallmeister.pg.backend.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AppUserRepository appUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        AppUser appUser = appUserRepository.findById(oAuth2User.getName()) /* getName() gives the ID of the GitHub user */
                .orElseGet(() -> saveUser(oAuth2User));
        appUser.setSimpleGrantedAuthorities(List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole())));
        return appUser;
    }

    public AppUser saveUser(OAuth2User oAuth2User) {
        return appUserRepository.save(AppUser.builder()
                .id(oAuth2User.getName())
                .username(oAuth2User.getAttributes().get("login").toString())
                .role(AppUserRoles.USER)
                .favoritePicturesIds(new ArrayList<>())
                .build()
        );
    }
}
