package com.unibooking.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthResponseDTO {

    private String token;
    private Long userId;
    private String email;
    private String phone;
    private String role;
}
