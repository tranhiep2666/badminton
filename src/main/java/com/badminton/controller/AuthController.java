package com.badminton.controller;

import com.badminton.dto.request.LoginRequest;
import com.badminton.dto.request.RegisterRequest;
import com.badminton.dto.response.RegisterResponse;
import com.badminton.dto.response.ResponseDTO;
import com.badminton.service.AuthService;
import com.badminton.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        RegisterResponse response = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
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
}