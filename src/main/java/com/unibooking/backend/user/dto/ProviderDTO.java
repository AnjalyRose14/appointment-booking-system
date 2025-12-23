package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDTO {

    private Long providerId;
    private String providerName; // Name of service (e.g., Haircut, Consultation)
    private String providerEmail;
    private String providerPhone;

    private String providerBusinessName;
    private String providerCategory;
    private String providerAddress;
    private String providerLocation;
    private String providerDescription;

    private LocalDateTime providerCreatedAt;

}