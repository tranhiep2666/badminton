package com.badminton.service;

import com.badminton.dto.request.LoginRequest;
import com.badminton.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(
            LoginRequest request
    );
}