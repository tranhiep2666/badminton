package com.badminton.controller;

import com.badminton.dto.response.UserResponse;
import com.badminton.service.AuthService;
import com.badminton.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Test
    void register_success() throws Exception {
        when(userService.register(any()))
                .thenReturn(
                        UserResponse.builder()
                                .id(1L)
                                .username("customer1")
                                .build()
                );
        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                        {
                                          "username":"customer1",
                                          "password":"123456",
                                          "fullName":"Customer",
                                          "email":"customer@gmail.com",
                                          "phoneNumber":"0123456789"
                                        }
                                        """)
                )
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    void login_success() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                        {
                                          "username":"customer1",
                                          "password":"123456"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void login_validation_failed() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                        {
                                          "username":"",
                                          "password":"123456"
                                        }
                                        """)
                )
                .andExpect(
                        status().isBadRequest()
                );
    }

    @Test
    void forgot_password_success() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/forgot-password")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content("""
                                        {
                                          "email":"customer@gmail.com"
                                        }
                                        """)
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    void verify_otp_success() throws Exception {

        mockMvc.perform(

                        post("/api/v1/auth/verify-otp")

                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )

                                .content("""
                                        {
                                          "email":"customer@gmail.com",
                                          "otp":"123456"
                                        }
                                        """)
                )

                .andExpect(
                        status().isOk()
                );
    }
}