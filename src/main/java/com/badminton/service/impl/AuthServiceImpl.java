package com.badminton.service.impl;

import com.badminton.dto.request.ChangePasswordRequest;
import com.badminton.dto.request.LoginRequest;
import com.badminton.dto.request.RefreshTokenRequest;
import com.badminton.dto.response.LoginResponse;
import com.badminton.dto.response.RefreshTokenResponse;
import com.badminton.entity.RefreshToken;
import com.badminton.entity.TokenBlacklist;
import com.badminton.entity.User;
import com.badminton.exception.BadRequestException;
import com.badminton.repository.RefreshTokenRepository;
import com.badminton.repository.TokenBlacklistRepository;
import com.badminton.repository.UserRepository;
import com.badminton.security.jwt.JwtService;
import com.badminton.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        try {

            authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(

                            request.getUsername(),

                            request.getPassword()
                    )
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            throw ex;
        }
        User user = userRepository
                .findByUsername(
                        request.getUsername()
                )
                .orElseThrow();
        String accessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getUsername()
                );
        refreshTokenRepository
                .deleteByUserId(
                        user.getId()
                );
        RefreshToken tokenEntity =
                RefreshToken.builder()

                        .token(refreshToken)

                        .user(user)

                        .expiryDate(
                                LocalDateTime.now()
                                        .plusDays(7)
                        )

                        .build();

        refreshTokenRepository
                .save(tokenEntity);
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(
                        user.getRole().name()
                )
                .build();
    }

    @Override
    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    ) {
        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByToken(
                                request.getRefreshToken()
                        )
                        .orElseThrow(() ->
                                new BadRequestException(
                                        "Invalid refresh token"
                                ));
        if(refreshToken
                .getExpiryDate()
                .isBefore(
                        LocalDateTime.now()
                )) {

            throw new BadRequestException(
                    "Refresh token expired"
            );
        }
        User user =
                refreshToken.getUser();
        String newAccessToken =
                jwtService.generateToken(

                        user.getUsername(),

                        user.getRole().name()
                );
        return RefreshTokenResponse
                .builder()

                .accessToken(
                        newAccessToken
                )

                .refreshToken(
                        refreshToken.getToken()
                )

                .build();
    }

    @Override
    public void logout(
            String token
    ) {

        Date expiration =
                jwtService.extractExpiration(
                        token
                );

        TokenBlacklist blacklist =
                TokenBlacklist.builder()

                        .token(token)

                        .expiryDate(
                                expiration.toInstant()
                                        .atZone(
                                                ZoneId.systemDefault()
                                        )
                                        .toLocalDateTime()
                        )

                        .build();

        tokenBlacklistRepository
                .save(blacklist);
    }

    @Override
    public void changePassword(
            String username,
            ChangePasswordRequest request
    ) {

        User user =
                userRepository
                        .findByUsername(
                                username
                        )
                        .orElseThrow();

        if(!passwordEncoder.matches(

                request.getOldPassword(),

                user.getPassword()

        )) {

            throw new BadRequestException(
                    "Old password is incorrect"
            );
        }

        user.setPassword(

                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);
    }
}
