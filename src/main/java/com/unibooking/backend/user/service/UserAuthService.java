package com.unibooking.backend.user.service;


import com.unibooking.backend.user.dto.UserAuthResponseDTO;
import com.unibooking.backend.user.dto.UserLoginDTO;
import com.unibooking.backend.user.dto.UserRegisterDTO;
import com.unibooking.backend.user.model.Role;
import com.unibooking.backend.user.model.UserModel;
import com.unibooking.backend.user.repository.UserRepository;
import com.unibooking.backend.user.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthService(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserAuthResponseDTO register(UserRegisterDTO request) {
        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserModel user = new UserModel();
        user.setUserEmail(request.getUserEmail());
        user.setUserName(request.getUserName());     // adjust if different name
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        user.setUserPhone(request.getUserPhone());
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        user.setEnabled(true);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new UserAuthResponseDTO(
                token,
                user.getUserId(),
                user.getUserEmail(),
                user.getUserPhone(),
                user.getRole().name()
        );
    }

    public UserAuthResponseDTO login(UserLoginDTO request) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                request.getUserEmail(),
                request.getUserPassword()
        );

        authenticationManager.authenticate(authToken);

        UserModel user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return new UserAuthResponseDTO(
                token,
                user.getUserId(),
                user.getUserEmail(),
                user.getUserPhone(),
                user.getRole().name()
        );
    }
}
