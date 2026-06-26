package com.riwi.talentboard.config;

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

import lombok.RequiredArgsConstructor;

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
                        // Endpoints públicos (Thymeleaf, Estáticos, Auth y Swagger)
                        .requestMatchers("/", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/api-docs/**").permitAll()

                        // REGLAS DE ROLES DE VACANTES (Unificado a Authority)
                        .requestMatchers(HttpMethod.POST, "/api/vacancies", "/api/vacancies/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.PUT, "/api/vacancies/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/api/vacancies/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/vacancies", "/api/vacancies/**").authenticated()

                        // ROLES DE POSTULACIONES (¡Excelente!)
                        .requestMatchers(HttpMethod.POST, "/api/applications", "/api/applications/**").hasAuthority("ROLE_CANDIDATE")
                        .requestMatchers(HttpMethod.PATCH, "/api/applications/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/applications", "/api/applications/**").authenticated()

                        // ROLES DE ENTREVISTAS (Corregido para permitir creación/lectura)
                        .requestMatchers(HttpMethod.POST, "/api/interviews", "/api/interviews/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.PATCH, "/api/interviews/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_RECRUITER")
                        .requestMatchers(HttpMethod.GET, "/api/interviews", "/api/interviews/**").authenticated()

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