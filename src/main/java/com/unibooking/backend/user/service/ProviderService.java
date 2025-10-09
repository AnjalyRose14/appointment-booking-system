package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderDTO;

import java.util.List;

public interface ProviderService {
    void registerProvider(ProviderDTO providerDTO);

    ProviderDTO getProviderByEmail(String email) throws ProviderNotFoundException;

    List<ProviderDTO> getAllProviders();

//    List<ProviderDTO> getProvidersByLocation(String location);

    List<ProviderDTO> getProvidersByCategory(String category);

    void deleteProviderByEmail(String email) throws ProviderNotFoundException;

    ProviderDTO updateProvider(ProviderDTO providerDTO) throws ProviderNotFoundException;
}
