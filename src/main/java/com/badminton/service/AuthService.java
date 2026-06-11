package com.badminton.service;

import com.badminton.dto.request.ChangePasswordRequest;
import com.badminton.dto.request.LoginRequest;
import com.badminton.dto.request.RefreshTokenRequest;
import com.badminton.dto.response.LoginResponse;
import com.badminton.dto.response.RefreshTokenResponse;

public interface AuthService {
    LoginResponse login(
            LoginRequest request
    );
    RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    );
    void logout(
            String token
    );
    void changePassword(
            String username,
            ChangePasswordRequest request
    );
}