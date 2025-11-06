package com.unibooking.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDTO {
        @Email @NotBlank
        private String userEmail;  // Identify user
        private String userName;
        private String userPhone;
        private String userPassword;  // optional
    }

