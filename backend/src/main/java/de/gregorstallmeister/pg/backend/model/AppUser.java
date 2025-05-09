package de.gregorstallmeister.pg.backend.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class AppUser implements OAuth2User {
    String id;
    String username;
    AppUserRoles role;
    List<String> favoritePicturesIds;

    @Transient
    private Map<String, Object> attributes;

    @Transient
    private List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public AppUser() {

    }

    public AppUser(String id, String username, AppUserRoles role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public AppUser(String id, String username, AppUserRoles role, List<String> favoritePicturesIds) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.favoritePicturesIds = favoritePicturesIds;
    }

    public AppUser(String id, String username, AppUserRoles role, List<String> favoritePicturesIds,
                   Map<String, Object> attributes, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.favoritePicturesIds = favoritePicturesIds;

        this.attributes = attributes;
        this.simpleGrantedAuthorities = simpleGrantedAuthorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    @Override
    public String getName() {
        return id;
    }
}
