package com.badminton.controller;

import com.badminton.dto.request.*;
import com.badminton.dto.response.ResponseDTO;
import com.badminton.dto.response.UserResponse;
import com.badminton.service.AuthService;
import com.badminton.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid
            @RequestBody RegisterRequest request) {

        UserResponse response =
                userService.register(request);

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ResponseDTO.builder()
                        .success(true)
                        .message("User registered successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid
            @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .success(true)
                        .message(
                                "Login successful"
                        )
                        .data(
                                authService.login(
                                        request
                                )
                        )
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(

            @RequestBody
            RefreshTokenRequest request
    ) {

        return ResponseEntity.ok(

                ResponseDTO.builder()

                        .success(true)

                        .message(
                                "Token refreshed successfully"
                        )

                        .data(
                                authService
                                        .refreshToken(
                                                request
                                        )
                        )

                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(

            HttpServletRequest request
    ) {

        String authHeader =
                request.getHeader(
                        "Authorization"
                );

        String token =
                authHeader.substring(7);

        authService.logout(token);

        return ResponseEntity.ok(

                ResponseDTO.builder()

                        .success(true)

                        .message(
                                "Logout successful"
                        )

                        .data(null)

                        .build()
        );
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            @Valid
            @RequestBody
            ChangePasswordRequest request
    ) {

        authService.changePassword(
                authentication.getName(),
                request
        );

        return ResponseEntity.ok(

                ResponseDTO.builder()

                        .success(true)

                        .message(
                                "Password changed successfully"
                        )

                        .data(null)

                        .build()
        );
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(

            @Valid
            @RequestBody
            ForgotPasswordRequest request
    ) {

        authService.forgotPassword(
                request
        );

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .success(true)
                        .message("OTP sent successfully")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(

            @Valid
            @RequestBody
            VerifyOtpRequest request
    ) {

        authService.verifyOtp(
                request
        );

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .success(true)
                        .message("OTP verified")
                        .data(null)
                        .build()
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(

            @Valid
            @RequestBody
            ResetPasswordRequest request
    ) {

        authService.resetPassword(
                request
        );

        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .success(true)
                        .message(
                                "Password reset successfully"
                        )
                        .data(null)
                        .build()
        );
    }
}