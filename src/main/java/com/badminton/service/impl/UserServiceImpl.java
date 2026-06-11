package com.badminton.service.impl;

import com.badminton.dto.request.RegisterRequest;
import com.badminton.dto.request.UpdateUserRequest;
import com.badminton.dto.response.UserResponse;
import com.badminton.entity.User;
import com.badminton.enums.Role;
import com.badminton.exception.ConflictException;
import com.badminton.exception.ResourceNotFoundException;
import com.badminton.repository.UserRepository;
import com.badminton.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {
            throw new ConflictException(
                    "Username already exists");
        }

        if(userRepository.existsByEmail(
                request.getEmail())) {
            throw new ConflictException(
                    "Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.ROLE_CUSTOMER)
                .enabled(true)
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .build();

    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        return mapToResponse(user);
    }

    @Override
    public Page<UserResponse> getUsers(
            String keyword,
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<User> users;

        if(keyword == null || keyword.isBlank()) {

            users = userRepository.findAll(pageable);

        } else {

            users = userRepository
                    .findByUsernameContainingIgnoreCase(
                            keyword,
                            pageable
                    );
        }

        return users.map(this::mapToResponse);
    }

    @Override
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request
    ) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        user.setFullName(
                request.getFullName()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setPhoneNumber(
                request.getPhoneNumber()
        );

        user.setEnabled(
                request.getEnabled()
        );

        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        userRepository.delete(user);
    }
























    private UserResponse mapToResponse(
            User user
    ) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .enabled(user.getEnabled())
                .build();
    }
}