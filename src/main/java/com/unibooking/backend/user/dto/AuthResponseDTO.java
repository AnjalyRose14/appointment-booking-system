package com.unibooking.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private Long userId;
    private String email;
    private String role;
}
