package com.badminton.service;

public interface EmailService {

    void sendOtp(
            String email,
            String otp
    );
}