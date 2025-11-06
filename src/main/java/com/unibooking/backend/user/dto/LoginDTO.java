package com.unibooking.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data // This automatically generates getters, setters, etc
public class LoginDTO {
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    private String userEmail;

    @NotBlank(message = "Password cannot be empty")
    private String userPassword;
}
