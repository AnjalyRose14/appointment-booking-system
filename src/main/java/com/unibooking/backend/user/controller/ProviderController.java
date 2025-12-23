package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.user.dto.ProviderDTO;
import com.unibooking.backend.user.dto.ProviderUpdateDTO;
import com.unibooking.backend.user.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;


    /* ---------- PUBLIC API (NO AUTH Required) ----------*/

    // List all services
    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAllServices() {
        return ResponseEntity.ok(providerService.getAllProviders());
    }


    // Get service details by ID (PUBLIC)
    @GetMapping("/{id}")
    public ResponseEntity<ProviderDTO> getServiceById(@PathVariable Long id)
            throws ProviderNotFoundException {
        return ResponseEntity.ok(providerService.getProviderById(id));
    }


    // Get services by location
    @GetMapping("/location")
    public ResponseEntity<List<ProviderDTO>> getServicesByLocation(@RequestParam String location) {
        return ResponseEntity.ok(providerService.getProvidersByLocation(location));
    }

    // Get services by category
    @GetMapping("/category")
    public ResponseEntity<List<ProviderDTO>> getServicesByCategory(@RequestParam String category) {
        return ResponseEntity.ok(providerService.getProvidersByCategory(category));
    }




    /* ---------- PROVIDER-ONLY APIs (JWT + ROLE_PROVIDER REQUIRED) ----------*/

    // Get current provider profile (SELF)
    @GetMapping("/currentProvider")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ProviderDTO> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();
        return ResponseEntity.ok(providerService.getProviderProfile(providerEmail));
    }

    // Update provider details (SELF)
    @PutMapping("/update")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ProviderDTO> updateProvider(
            @RequestBody ProviderUpdateDTO updateDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();

        ProviderDTO updated =
                providerService.updateProviderProfile(providerEmail, updateDTO);

        return ResponseEntity.ok(updated);
    }

    // Delete provider  (SELF)
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteProvider(
            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();
        providerService.deleteProviderByEmail(providerEmail);

        return ResponseEntity.noContent().build();
    }



    /* ---------- ADMIN ---------- */

    // ADMIN: delete any provider by ID
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProviderByAdmin(@PathVariable Long id) {
        providerService.deleteProviderByAdmin(id);
        return ResponseEntity.ok("User profile deleted successfully!");
    }

}