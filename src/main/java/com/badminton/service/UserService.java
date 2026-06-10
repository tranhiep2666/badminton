package com.badminton.service;

import com.badminton.dto.request.RegisterRequest;
import com.badminton.dto.request.UpdateUserRequest;
import com.badminton.dto.response.RegisterResponse;
import com.badminton.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    RegisterResponse register(RegisterRequest request);

    UserResponse getUserById(Long id);

    Page<UserResponse> getUsers(
            String keyword,
            int page,
            int size
    );

    UserResponse updateUser(
            Long id,
            UpdateUserRequest request
    );

    void deleteUser(Long id);
}