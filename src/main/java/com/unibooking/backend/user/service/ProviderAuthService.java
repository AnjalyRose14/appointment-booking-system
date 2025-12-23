package com.unibooking.backend.user.service;

import com.unibooking.backend.user.dto.ProviderAuthResponseDTO;
import com.unibooking.backend.user.dto.ProviderLoginDTO;
import com.unibooking.backend.user.dto.ProviderRegisterDTO;
import com.unibooking.backend.user.model.ProviderModel;
import com.unibooking.backend.user.model.Role;
import com.unibooking.backend.user.repository.ProviderRepository;
import com.unibooking.backend.user.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ProviderAuthService {

    private final ProviderRepository providerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ProviderAuthService(ProviderRepository providerRepository,
                               PasswordEncoder passwordEncoder,
                               JwtService jwtService,
                               AuthenticationManager authenticationManager) {

        this.providerRepository = providerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public ProviderAuthResponseDTO register(ProviderRegisterDTO dto) {

        if (providerRepository.existsByProviderEmail(dto.getProviderEmail())) {
            throw new IllegalArgumentException("Provider already exists");
        }

        ProviderModel provider = ProviderModel.builder()
                .providerName(dto.getProviderName())
                .providerEmail(dto.getProviderEmail())
                .providerPassword(passwordEncoder.encode(dto.getProviderPassword()))
                .providerPhone(dto.getProviderPhone())
                .providerBusinessName(dto.getProviderBusinessName())
                .providerCategory(dto.getProviderCategory())
                .providerAddress(dto.getProviderAddress())
                .providerLocation(dto.getProviderLocation())
                .providerDescription(dto.getProviderDescription())
                .providerCreatedAt(LocalDateTime.now())
                .role(Role.PROVIDER)
                .enabled(true)
                .build();

        providerRepository.save(provider);

        String token = jwtService.generateToken(provider);

        return new ProviderAuthResponseDTO(
                token,
                provider.getProviderId(),
                provider.getProviderEmail(),
                provider.getRole().name()
        );
    }

    public ProviderAuthResponseDTO login(ProviderLoginDTO dto) {

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                dto.getProviderEmail(),
                dto.getProviderPassword()
        );


        authenticationManager.authenticate(authToken);

        ProviderModel provider = providerRepository.findByProviderEmail(dto.getProviderEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        String token = jwtService.generateToken(provider);

        return new ProviderAuthResponseDTO(
                token,
                provider.getProviderId(),
                provider.getProviderEmail(),
                provider.getRole().name()
        );
    }
}
