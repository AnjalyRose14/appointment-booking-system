package com.unibooking.backend.user.controller;

import com.unibooking.backend.user.dto.*;
import com.unibooking.backend.user.service.UserAuthService;
import com.unibooking.backend.user.service.ProviderAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthService authService;
    private final ProviderAuthService providerAuthService;

    public AuthController(
            UserAuthService authService,
            ProviderAuthService providerAuthService) {
        this.authService = authService;
        this.providerAuthService = providerAuthService;
    }



    // -------- USER --------
    @PostMapping("/user/register")
    public ResponseEntity<UserAuthResponseDTO> registerUser(
            @Valid @RequestBody UserRegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserAuthResponseDTO> loginUser(
            @Valid @RequestBody UserLoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }


    // -------- PROVIDER --------
    @PostMapping("/provider/register")
    public ResponseEntity<ProviderAuthResponseDTO> registerProvider(
            @RequestBody ProviderRegisterDTO dto) {
        return ResponseEntity.ok(providerAuthService.register(dto));
    }

    @PostMapping("/provider/login")
    public ResponseEntity<ProviderAuthResponseDTO> loginProvider(
            @RequestBody ProviderLoginDTO dto) {
        return ResponseEntity.ok(providerAuthService.login(dto));
    }


}
