package com.badminton.service;

import com.badminton.dto.request.RegisterRequest;
import com.badminton.dto.response.UserResponse;
import com.badminton.entity.User;
import com.badminton.exception.ConflictException;
import com.badminton.exception.ResourceNotFoundException;
import com.badminton.repository.UserRepository;
import com.badminton.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("customer1");
        request.setPassword("123456");
        request.setFullName("Nguyen Van A");
        request.setEmail("a@gmail.com");
        request.setPhoneNumber("0123456789");
        when(userRepository.existsByUsername("customer1")).thenReturn(false);
        when(userRepository.existsByEmail("a@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("ENCODED");
        UserResponse response = userService.register(request);
        assertEquals(
                "customer1",
                response.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_duplicate_username() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("customer1");
        when(userRepository.existsByUsername("customer1")).thenReturn(true);
        assertThrows(
                ConflictException.class,
                () -> userService.register(request)
        );
    }

    @Test
    void getUserById_success() {
        User user = User.builder()
                .id(1L)
                .username("customer1")
                .fullName("Customer")
                .email("customer@gmail.com")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse response = userService.getUserById(1L);
        assertEquals(
                "customer1",
                response.getUsername()
        );
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getUserById(999L)
        );
    }

    @Test
    void deleteUser_success() {
        User user = User.builder()
                .id(1L)
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository).delete(user);
    }
}