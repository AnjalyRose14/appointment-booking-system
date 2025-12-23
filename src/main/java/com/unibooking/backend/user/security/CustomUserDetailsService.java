package com.unibooking.backend.user.security;

import com.unibooking.backend.user.repository.ProviderRepository;
import com.unibooking.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final ProviderRepository providerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        return userRepository.findByUserEmail(email)
                .map(user -> (UserDetails) user)
                .orElseGet(() ->
                        providerRepository.findByProviderEmail(email)
                                .map(provider -> (UserDetails) provider)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException(
                                                "User or Provider not found"))
                );
    }
}
