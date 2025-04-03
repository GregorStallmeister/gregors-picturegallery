package de.gregorstallmeister.backend.model;

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
    String role;

    @Transient
    private Map<String, Object> attributes;

    @Transient
    private List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public AppUser() {

    }

    public AppUser(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public AppUser(String id, String username, String role, Map<String, Object> attributes, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        this.id = id;
        this.username = username;
        this.role = role;
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
