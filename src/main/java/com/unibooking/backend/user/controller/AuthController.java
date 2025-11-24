package com.unibooking.backend.user.controller;

import com.unibooking.backend.user.dto.AuthResponseDTO;
import com.unibooking.backend.user.dto.LoginDTO;
import com.unibooking.backend.user.dto.RegisterDTO;
import com.unibooking.backend.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
