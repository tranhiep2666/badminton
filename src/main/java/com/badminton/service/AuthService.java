package com.badminton.service;

import com.badminton.dto.request.*;
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
    void forgotPassword(
            ForgotPasswordRequest request
    );

    void verifyOtp(
            VerifyOtpRequest request
    );

    void resetPassword(
            ResetPasswordRequest request
    );
}