package com.badminton.config;

import com.badminton.security.filter.JwtAuthenticationFilter;
import com.badminton.security.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config
    )throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider
    authenticationProvider(
            PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setUserDetailsService(
                userDetailsService
        );
        provider.setPasswordEncoder(
                passwordEncoder
        );
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED,
                                                "Unauthorized"
                                        )
                        )

                        .accessDeniedHandler(
                                (request, response, accessDeniedException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_FORBIDDEN,
                                                "Forbidden"
                                        )
                        )
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers(
                                "/api/v1/auth/**"
                        ).permitAll()

                        // ADMIN
                        .requestMatchers(
                                "/api/v1/users/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/bookings/*/confirm",
                                "/api/v1/bookings/*/cancel"
                        ).hasRole("ADMIN")

                        // CUSTOMER
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/bookings"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/bookings/history/**"
                        ).hasRole("CUSTOMER")

                        // LOGIN REQUIRED
                        .requestMatchers(
                                "/api/v1/files/**"
                        ).authenticated()

                        .anyRequest()
                        .authenticated()
                );
        return http.build();
    }
}