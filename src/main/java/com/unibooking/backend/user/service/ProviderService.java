package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderLoginDTO;
import com.unibooking.backend.user.dto.ProviderRegisterDTO;
import com.unibooking.backend.user.dto.ProviderResponseDTO;
import com.unibooking.backend.user.dto.ProviderUpdateDTO;

import java.util.List;

public interface ProviderService {

    //Register Provider
    ProviderResponseDTO registerProvider(ProviderRegisterDTO registerDTO);

    //Get all providers
    List<ProviderResponseDTO> getAllProviders();

    //Login Provider
    String loginProvider(ProviderLoginDTO loginDTO);

    //Get provider by email
    ProviderResponseDTO getProviderByEmail(String email) throws ProviderNotFoundException;


    //Get providers by location
    List<ProviderResponseDTO> getProvidersByLocation(String location);

    //Get providers by category
    List<ProviderResponseDTO> getProvidersByCategory(String category);

    //Update Provider
    ProviderResponseDTO updateProvider(ProviderUpdateDTO updateDTO) throws ProviderNotFoundException;

    //Delete Provider
    void deleteProviderByEmail(String providerEmail) throws ProviderNotFoundException;

}
