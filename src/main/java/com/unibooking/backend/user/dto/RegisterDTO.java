package com.unibooking.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RegisterDTO {

    @NotBlank(message = "Name cannot be empty")
    private String userName;

    @Email(message = "Invalid email format")
    private String userEmail;

    @NotBlank(message = "Password cannot be empty")
    private String userPassword;

    private String userPhone;
}