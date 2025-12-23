package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private Role role;
}
