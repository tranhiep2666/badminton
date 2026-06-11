package com.badminton.service.impl;

import com.badminton.dto.request.LoginRequest;
import com.badminton.dto.response.LoginResponse;
import com.badminton.entity.User;
import com.badminton.repository.UserRepository;
import com.badminton.security.jwt.JwtService;
import com.badminton.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                        request.getUsername(),

                        request.getPassword()
                )
        );
        User user = userRepository
                .findByUsername(
                        request.getUsername()
                )
                .orElseThrow();
        String token =
                jwtService.generateToken(

                        user.getUsername(),

                        user.getRole().name()
                );
        return LoginResponse.builder()
                .accessToken(token)
                .role(
                        user.getRole().name()
                )
                .build();
    }
}
