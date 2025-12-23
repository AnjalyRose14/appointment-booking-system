package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.model.ProviderModel;
import com.unibooking.backend.user.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;


            //Get all providers
            @Override
            public List<ProviderDTO> getAllProviders() {
                return providerRepository.findAll()
                        .stream()
                        .map(this::ConvertToDTO)
                        .collect(Collectors.toList());
            }

            //Converts Entity to DTO
            private ProviderDTO ConvertToDTO(ProviderModel provider) {
                    ProviderDTO dto = new ProviderDTO();

                        dto.setProviderId(provider.getProviderId());
                        dto.setProviderName(provider.getProviderName());
                        dto.setProviderEmail(provider.getProviderEmail());
                        dto.setProviderPhone(provider.getProviderPhone());
                        dto.setProviderBusinessName(provider.getProviderBusinessName());
                        dto.setProviderCategory(provider.getProviderCategory());
                        dto.setProviderAddress(provider.getProviderAddress());
                        dto.setProviderLocation(provider.getProviderLocation());
                        dto.setProviderDescription(provider.getProviderDescription());

                return dto;

            }

            @Override
            public ProviderDTO getProviderById(Long providerId) {

                ProviderModel provider = providerRepository.findById(providerId)
                        .orElseThrow(() ->
                                new ProviderNotFoundException(
                                        "Provider not found with id: " + providerId));

                return ConvertToDTO(provider);
            }


            //Get by location
            @Override
            public List<ProviderDTO> getProvidersByLocation(String location) {
                return providerRepository.findByProviderLocation(location)
                        .stream()
                        .map(this::ConvertToDTO)
                        .collect(Collectors.toList());
            }

            //Get by Category
            @Override
            public List<ProviderDTO> getProvidersByCategory(String category) {
                return providerRepository.findByProviderCategory(category)
                        .stream()
                        .map(this::ConvertToDTO)
                        .collect(Collectors.toList());
            }

            //Get provider by email (email from JWT token)
            @Override
            public ProviderDTO getProviderProfile(String email) throws ProviderNotFoundException {
                ProviderModel provider = providerRepository.findByProviderEmail(email)
                        .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + email));

                return ConvertToDTO(provider);
            }

            //Update by provider
            @Override
            @Transactional
            public ProviderDTO updateProviderProfile(String email, ProviderUpdateDTO updateDTO) throws ProviderNotFoundException{

                ProviderModel provider = providerRepository.findByProviderEmail(email)
                        .orElseThrow(() -> new ProviderNotFoundException("Provider not found:" + email));

                // update allowed fields only
                if (updateDTO.getProviderName() != null)
                    provider.setProviderName(updateDTO.getProviderName());

                if (updateDTO.getProviderPhone() != null)
                    provider.setProviderPhone(updateDTO.getProviderPhone());

                if (updateDTO.getProviderBusinessName() != null)
                    provider.setProviderBusinessName(updateDTO.getProviderBusinessName());

                if (updateDTO.getProviderCategory() != null)
                    provider.setProviderCategory(updateDTO.getProviderCategory());

                if (updateDTO.getProviderAddress() != null)
                    provider.setProviderAddress(updateDTO.getProviderAddress());

                if (updateDTO.getProviderLocation() != null)
                    provider.setProviderLocation(updateDTO.getProviderLocation());

                if (updateDTO.getProviderDescription() != null)
                    provider.setProviderDescription(updateDTO.getProviderDescription());

                if (updateDTO.getProviderPassword() != null && !updateDTO.getProviderPassword().isBlank()) {
                    provider.setProviderPassword(passwordEncoder.encode(updateDTO.getProviderPassword()));
                }

                providerRepository.save(provider);
                return null;
            }

            // Delete own Account (Authenticated User)
            @Override
            public void deleteProviderByEmail(String email) throws ProviderNotFoundException {
                ProviderModel provider = providerRepository.findByProviderEmail(email)
                        .orElseThrow(() -> new ProviderNotFoundException("Provider not found"));

                providerRepository.delete(provider);
            }

            //Delete provider by ID (Admin)
            @Override
            public void deleteProviderByAdmin(Long id) throws ProviderNotFoundException {
                if (!providerRepository.existsById(id)) {
                    throw new ProviderNotFoundException("Provider with ID " + id + "not found");
                }
                providerRepository.deleteById(id);
            }

    @Override
    public Long getProviderIdByEmail(String email) {
        return providerRepository.findByProviderEmail(email)
                .orElseThrow(() -> new RuntimeException("Provider not found"))
                .getProviderId();
    }
}
