package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegisterDTO {

    @NotBlank(message = "Name cannot be empty")
    private String userName;

    @Email(message = "Invalid email format")
    private String userEmail;

    @NotBlank(message = "Password cannot be empty")
    private String userPassword;

    private String userPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}