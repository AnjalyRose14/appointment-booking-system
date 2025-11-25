package com.unibooking.backend.user.controller;

import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;
import com.unibooking.backend.user.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SlotService slotService;

    @PostMapping("/create")
    public ResponseEntity<SlotResponseDTO> createSlot(@RequestBody SlotCreateDTO createDTO) {
        return ResponseEntity.ok(slotService.createSlot(createDTO));
    }

    @PutMapping("/update-status")
    public ResponseEntity<SlotResponseDTO> updateSlot(@RequestBody SlotUpdateDTO updateDTO) {
        return ResponseEntity.ok(slotService.updateSlotStatus(updateDTO));
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<SlotResponseDTO>> getSlotsByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(slotService.getSlotsByProvider(providerId));
    }

    @GetMapping("/availability/{slotId}")
    public ResponseEntity<Boolean> isSlotAvailable(@PathVariable Long slotId) {
        return ResponseEntity.ok(slotService.isSlotAvailable(slotId));
    }

    @DeleteMapping("/delete/{slotId}")
    public ResponseEntity<String> deleteSlot(@PathVariable Long slotId) {
        slotService.deleteSlot(slotId);
        return ResponseEntity.ok("Slot deleted successfully.");
    }
}
