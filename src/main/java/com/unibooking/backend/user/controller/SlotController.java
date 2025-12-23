package com.unibooking.backend.user.controller;

import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;
import com.unibooking.backend.user.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    /* ---------- PROVIDER ONLY ---------- */

    // Create slot (SELF)
    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<SlotResponseDTO> createSlot(
            @RequestBody SlotCreateDTO createDTO,

            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();
        return ResponseEntity.ok(
                slotService.createSlot(providerEmail, createDTO)
        );
    }

    // Update slot status (SELF)
    @PutMapping("/{slotId}/status")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<SlotResponseDTO> updateSlotStatus(
            @PathVariable Long slotId,
            @RequestBody SlotUpdateDTO updateDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();
        return ResponseEntity.ok(
                slotService.updateSlotStatus(slotId, providerEmail, updateDTO)
        );
    }

    // Delete slot (SELF)
    @DeleteMapping("/{slotId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteSlot(
            @PathVariable Long slotId,
            @AuthenticationPrincipal UserDetails userDetails) {

        String providerEmail = userDetails.getUsername();
        slotService.deleteSlot(slotId, providerEmail);
        return ResponseEntity.noContent().build();
    }

    // Get slots by provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<SlotResponseDTO>> getSlotsByProvider(
            @PathVariable Long providerId) {

        return ResponseEntity.ok(
                slotService.getSlotsByProvider(providerId)
        );
    }

    // Check slot availability (Authenticated USER / PROVIDER)
    @GetMapping("/{slotId}/availability")
    @PreAuthorize("hasAnyRole('USER','PROVIDER')")
    public ResponseEntity<Boolean> isSlotAvailable(
            @PathVariable Long slotId) {

        return ResponseEntity.ok(
                slotService.isSlotAvailable(slotId)
        );
    }
}
