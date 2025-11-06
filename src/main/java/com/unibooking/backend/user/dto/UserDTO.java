package com.unibooking.backend.user.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
}

