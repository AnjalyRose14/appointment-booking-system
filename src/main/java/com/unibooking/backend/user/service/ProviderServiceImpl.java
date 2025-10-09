package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderDTO;
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

    @Override
    public void registerProvider(ProviderDTO providerDTO) {
        ProviderModel provider = new ProviderModel();
        provider.setProviderName(providerDTO.getProviderName());
        provider.setProviderEmail(providerDTO.getProviderEmail());
        provider.setProviderPassword(providerDTO.getProviderPassword());
        provider.setProviderPhone(providerDTO.getProviderPhone());
        provider.setProviderBusinessName(providerDTO.getProviderBusinessName());
        provider.setProviderCategory(providerDTO.getProviderCategory());
        provider.setProviderAddress(providerDTO.getProviderAddress());
        provider.setProviderLocation(providerDTO.getProviderLocation());
        provider.setProviderDescription(providerDTO.getProviderDescription());
        provider.setProviderCreatedAt(LocalDateTime.now());

        providerRepository.save(provider);
    }

    @Override
    public ProviderDTO getProviderByEmail(String email) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findByProviderEmail(email)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + email));

        return mapToDTO(provider);
    }

    @Override
    public List<ProviderDTO> getAllProviders() {
        return providerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<ProviderDTO> getProvidersByLocation(String location) {
//        return providerRepository.findByProviderLocation(location)
//                .stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<ProviderDTO> getProvidersByCategory(String category) {
        return providerRepository.findByProviderCategory(category)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProviderByEmail(String email) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findByProviderEmail(email)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + email));
        providerRepository.delete(provider);
    }

    @Override
    public ProviderDTO updateProvider(ProviderDTO providerDTO) throws ProviderNotFoundException {
        ProviderModel provider = providerRepository.findByProviderEmail(providerDTO.getProviderEmail())
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found with email: " + providerDTO.getProviderEmail()));

        provider.setProviderName(providerDTO.getProviderName());
        provider.setProviderPhone(providerDTO.getProviderPhone());
        provider.setProviderBusinessName(providerDTO.getProviderBusinessName());
        provider.setProviderCategory(providerDTO.getProviderCategory());
        provider.setProviderAddress(providerDTO.getProviderAddress());
        provider.setProviderLocation(providerDTO.getProviderLocation());
        provider.setProviderDescription(providerDTO.getProviderDescription());

        ProviderModel updated = providerRepository.save(provider);
        return mapToDTO(updated);
    }

    private ProviderDTO mapToDTO(ProviderModel provider) {
        return new ProviderDTO(
                provider.getProviderId(),
                provider.getProviderName(),
                provider.getProviderEmail(),
                provider.getProviderPassword(),
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
