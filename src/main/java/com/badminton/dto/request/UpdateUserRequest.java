package com.badminton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

    private String phoneNumber;

    private Boolean enabled;
}