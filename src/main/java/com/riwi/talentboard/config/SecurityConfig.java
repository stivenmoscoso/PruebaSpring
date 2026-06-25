package com.riwi.talentboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitado porque usamos JWT sin cookies
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (Registro y Login)
                        .requestMatchers("/api/auth/**").permitAll()

                        // REGLAS DE ROLES DE VACANTES
                        .requestMatchers(HttpMethod.POST, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/api/vacancies/**").hasAnyRole("ADMIN", "RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/vacancies/**").authenticated() // Cualquiera logueado ve vacantes

                        // ROLES DE POSTULACIONES
                        .requestMatchers(HttpMethod.POST, "/api/applications/**").hasRole("CANDIDATE") // Solo postulantes
                        .requestMatchers(HttpMethod.PATCH, "/api/applications/**").hasAnyRole("ADMIN", "RECRUITER") // Cambios de estado
                        .requestMatchers(HttpMethod.GET, "/api/applications/candidate/**").authenticated()

                        // ROLES DE ENTREVISTAS
                        .requestMatchers("/api/interviews/**").hasAnyRole("ADMIN", "RECRUITER") // Solo staff maneja agendas

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API sin estado
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encriptación robusta para contraseñas
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}