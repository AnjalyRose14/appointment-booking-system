package com.unibooking.backend.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProviderUpdateDTO {

    private String providerName;
    private String providerPhone;
    private String providerPassword;  // optional


    private String providerBusinessName;
    private String providerCategory;
    private String providerAddress;
    private String providerLocation;
    private String providerDescription;

}
