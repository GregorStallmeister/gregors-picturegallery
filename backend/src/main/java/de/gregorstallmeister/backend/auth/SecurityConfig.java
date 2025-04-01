package de.gregorstallmeister.backend.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @SuppressWarnings("squid:S4502")
    public SecurityFilterChain securityFilterChain(HttpSecurity currywurst) throws Exception {
        currywurst
                // bei username password muss das csrf definitiv enabled sein, bei oauth wird das für uns geregelt
                .csrf(AbstractHttpConfigurer::disable) // Compliant //cross site reforgery token, gegen hacker, anfrage muss immer vom selben host kommen
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/auth/me").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .logout(l -> l.logoutSuccessUrl(appUrl + "/logout")) // change string if you want to navigate somewhere
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(o -> o.defaultSuccessUrl(appUrl + "/home")); // change string if you want to navigate somewhere

        return currywurst.build();
    }
}
