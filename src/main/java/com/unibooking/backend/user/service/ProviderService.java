package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.*;

import java.util.List;

public interface ProviderService {

    //Get all providers
    List<ProviderDTO> getAllProviders();

    //Get provider by id
    ProviderDTO getProviderById(Long id);

    //Get providers by location
    List<ProviderDTO> getProvidersByLocation(String location);

    //Get providers by category
    List<ProviderDTO> getProvidersByCategory(String category);

    //Get current provider by email (email extracted from JWT)
    ProviderDTO getProviderProfile(String email) throws ProviderNotFoundException;

    //  Update an existing provider profile identified by email (the caller's email from token)
    ProviderDTO updateProviderProfile(String email, ProviderUpdateDTO dto) throws ProviderNotFoundException;

    //Delete the provider identified by email (self-delete use-case)
    void deleteProviderByEmail(String providerEmail) throws ProviderNotFoundException;

    // Delete a provider by id (admin-only)
    void deleteProviderByAdmin(Long id) throws ProviderNotFoundException;

    Long getProviderIdByEmail(String email);
}
