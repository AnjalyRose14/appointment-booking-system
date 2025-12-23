package com.unibooking.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderAuthResponseDTO {

    private String token;
    private Long providerId;
    private String email;
    private String role;
}
