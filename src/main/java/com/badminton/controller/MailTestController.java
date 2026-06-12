package com.badminton.controller;

import com.badminton.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class MailTestController {

    private final EmailService emailService;

    @GetMapping("/mail")
    public String testMail() {

        emailService.sendOtp(
                "hiep5apy@gmail.com",
                "123456"
        );

        return "Mail sent";
    }
}
