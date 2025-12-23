package com.unibooking.backend.user.service;

import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;

import java.util.List;

public interface SlotService {

    SlotResponseDTO createSlot(String providerEmail, SlotCreateDTO dto);

    SlotResponseDTO updateSlotStatus(
            Long slotId,
            String providerEmail,
            SlotUpdateDTO dto
    );

    List<SlotResponseDTO> getSlotsByProvider(Long providerId);

    void deleteSlot(Long slotId, String providerEmail);

    boolean isSlotAvailable(Long slotId);


}
