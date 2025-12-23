package com.unibooking.backend.user.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {

        private String userName;
        private String userPhone;
        private String userPassword;  // optional
    }

