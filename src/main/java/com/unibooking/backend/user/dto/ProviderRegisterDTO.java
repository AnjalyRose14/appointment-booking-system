package com.unibooking.backend.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProviderRegisterDTO {

    @NotBlank(message = "Provider name is required")
    private String providerName; // Name of service (e.g., Haircut, Consultation)

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String providerEmail;

    @NotBlank(message = "Password is required")
    private String providerPassword;

    private String providerPhone;

    private String providerBusinessName;
    private String providerCategory;
    private String providerAddress;
    private String providerLocation;
    private String providerDescription;

}
