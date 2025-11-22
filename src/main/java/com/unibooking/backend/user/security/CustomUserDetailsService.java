package com.unibooking.backend.user.security;

import com.unibooking.backend.user.model.UserModel;
import com.unibooking.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserModel user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword()) // hashed
                .roles(user.getRole().name()) // ROLE_USER / ROLE_PROVIDER etc
                .disabled(!user.isEnabled())
                .build();
    }
}
