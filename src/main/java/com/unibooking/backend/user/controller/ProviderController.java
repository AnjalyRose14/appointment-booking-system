package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.ProviderAlreadyExistsException;
import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderLoginDTO;
import com.unibooking.backend.user.dto.ProviderRegisterDTO;
import com.unibooking.backend.user.dto.ProviderResponseDTO;
import com.unibooking.backend.user.dto.ProviderUpdateDTO;
import com.unibooking.backend.user.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    // Register a new service (business/organisation)
    @PostMapping("/register")
    public ResponseEntity<?> registerProvider(@RequestBody ProviderRegisterDTO registerDTO) {
        try {
            providerService.registerProvider(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ProviderAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    // List all services
    @GetMapping
    public ResponseEntity<List<ProviderResponseDTO>> getAllServices() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }

    //Login using email and password
    @PostMapping("/login")
    public ResponseEntity<String> loginProvider(@RequestBody ProviderLoginDTO loginDTO) {
        String result = providerService.loginProvider(loginDTO);
        return ResponseEntity.ok(result);
    }


    // Get service details by email
    @GetMapping("/details")
    public ResponseEntity<?> getServiceDetails(@RequestParam String email) {
        try {
            ProviderResponseDTO provider = providerService.getProviderByEmail(email);
            return ResponseEntity.ok(provider);
        } catch (ProviderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    // Get services by location
    @GetMapping("/location")
    public ResponseEntity<List<ProviderResponseDTO>> getServicesByLocation(@RequestParam String location) {
        return ResponseEntity.ok(providerService.getProvidersByLocation(location));
    }

    // Get services by category
    @GetMapping("/category")
    public ResponseEntity<List<ProviderResponseDTO>> getServicesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(providerService.getProvidersByCategory(category));
    }

    // Delete provider by email
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProvider(@RequestParam String email) {
        try {
            providerService.deleteProviderByEmail(email);
            return ResponseEntity.ok("Provider deleted successfully.");
        } catch (ProviderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // Update provider details
    @PutMapping("/update")
    public ResponseEntity<?> updateProvider(@RequestBody ProviderUpdateDTO updateDTO) {
        try {
            ProviderResponseDTO updatedProvider = providerService.updateProvider(updateDTO);
            return ResponseEntity.ok(updatedProvider);
        } catch (ProviderNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}