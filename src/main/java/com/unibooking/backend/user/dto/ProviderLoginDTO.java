package com.unibooking.backend.user.dto;


import lombok.Data;

@Data
public class ProviderLoginDTO {

    private String providerEmail;
    private String providerPassword;

}
