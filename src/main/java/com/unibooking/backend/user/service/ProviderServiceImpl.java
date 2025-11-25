package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderLoginDTO;
import com.unibooking.backend.user.dto.ProviderRegisterDTO;
import com.unibooking.backend.user.dto.ProviderResponseDTO;
import com.unibooking.backend.user.dto.ProviderUpdateDTO;
import com.unibooking.backend.user.model.ProviderModel;
import com.unibooking.backend.user.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;

    //Register
    @Override
    public ProviderResponseDTO registerProvider(ProviderRegisterDTO registerDTO) {

        // Check if email already exists
        providerRepository.findByProviderEmail(registerDTO.getProviderEmail())
                .ifPresent(p -> {
                    throw new RuntimeException("Provider already exists");
                });

        ProviderModel provider = ProviderModel.builder()
                .providerName(registerDTO.getProviderName())
                .providerEmail(registerDTO.getProviderEmail())
                .providerPassword(registerDTO.getProviderPassword())
                .providerPhone(registerDTO.getProviderPhone())
                .providerBusinessName(registerDTO.getProviderBusinessName())
                .providerCategory(registerDTO.getProviderCategory())
                .providerAddress(registerDTO.getProviderAddress())
                .providerLocation(registerDTO.getProviderLocation())
                .providerDescription(registerDTO.getProviderDescription())
                .providerCreatedAt(LocalDateTime.now())
                .build();

        ProviderModel saved = providerRepository.save(provider);
        return mapToResponseDTO(saved);

    }

    //Login
    @Override
    public String loginProvider(ProviderLoginDTO loginDTO) {
        ProviderModel provider = providerRepository.findByProviderEmail(loginDTO.getProviderEmail())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (!provider.getProviderPassword().equals(loginDTO.getProviderPassword())) {
            return "Invalid password";
        }
        return "Login successful";
    }


    //Get by email
    @Override
    public ProviderResponseDTO getProviderByEmail(String email) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findByProviderEmail(email)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + email));

        return mapToResponseDTO(provider);
    }

    //Get all providers
    @Override
    public List<ProviderResponseDTO> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Get by location
    @Override
    public List<ProviderResponseDTO> getProvidersByLocation(String location) {
        return providerRepository.findByProviderLocation(location)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Get by Category
    @Override
    public List<ProviderResponseDTO> getProvidersByCategory(String category) {
        return providerRepository.findByProviderCategory(category)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //Delete
    @Override
    public void deleteProviderByEmail(String email) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findByProviderEmail(email)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + email));
        providerRepository.delete(provider);
    }

    //Update
    @Override
    public ProviderResponseDTO updateProvider(ProviderUpdateDTO updateDTO) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findById(updateDTO.getProviderId())
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + updateDTO.getProviderId()));

        provider.setProviderName(updateDTO.getProviderName());
        provider.setProviderPhone(updateDTO.getProviderPhone());
        provider.setProviderBusinessName(updateDTO.getProviderBusinessName());
        provider.setProviderCategory(updateDTO.getProviderCategory());
        provider.setProviderAddress(updateDTO.getProviderAddress());
        provider.setProviderLocation(updateDTO.getProviderLocation());
        provider.setProviderDescription(updateDTO.getProviderDescription());

        ProviderModel updated = providerRepository.save(provider);
        return mapToResponseDTO(updated);
    }

//MAPPER - Converts entity to response DTO
    private ProviderResponseDTO mapToResponseDTO(ProviderModel provider) {
        return new ProviderResponseDTO(

                provider.getProviderId(),
                provider.getProviderName(),
                provider.getProviderEmail(),
                provider.getProviderPhone(),
                provider.getProviderBusinessName(),
                provider.getProviderCategory(),
                provider.getProviderAddress(),
                provider.getProviderLocation(),
                provider.getProviderDescription(),
                provider.getProviderCreatedAt()
        );
    }
}
