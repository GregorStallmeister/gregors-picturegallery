package de.gregorstallmeister.backend.auth;

import de.gregorstallmeister.backend.model.AppUserRoles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.url}")
//    @Value("http://localhost:5173")
    private String appUrl;

    @Bean
    @SuppressWarnings("squid:S4502") // to confirm, that disabling csfr is safe in my application
    public SecurityFilterChain securityFilterChain(HttpSecurity currywurst) throws Exception {
        currywurst // My coach Marcell said, the name does not matter, I can name this object "currywurst" ;-)
                // Thanks to him for all his coaching, my best wishes and greetings!
                // And greetings to Dortmund, where he lives, next to my hometown Hagen.
                // In both cities you can eat currywurst (curry sausage), a popular delicious fast food, typical for the Ruhr area.

                // with login by username password csrf must be enabled, with oauth we need not to care about it.
                .csrf(AbstractHttpConfigurer::disable) // Compliant //cross site reforgery token, against hackers, request must always come from the same host
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.DELETE, "/api/picture/{id}").hasRole(AppUserRoles.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/picture/{id}").hasRole(AppUserRoles.ADMIN.toString())
                        .requestMatchers(HttpMethod.POST, "/api/picture").hasRole(AppUserRoles.ADMIN.toString())
                        .anyRequest().permitAll()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(l -> l.logoutSuccessUrl(appUrl)) // change string if you want to navigate somewhere
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(o -> o.defaultSuccessUrl(appUrl + "/home")); // change string if you want to navigate somewhere

        return currywurst.build();
    }
}
