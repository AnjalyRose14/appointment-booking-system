package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.SlotNotFoundException;
import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;

import java.util.List;

public interface SlotService {

    SlotResponseDTO createSlot(SlotCreateDTO createDTO);

    SlotResponseDTO updateSlotStatus(SlotUpdateDTO updateDTO) throws SlotNotFoundException;

    List<SlotResponseDTO> getSlotsByProvider(Long providerId);

    boolean isSlotAvailable(Long slotId);

    void deleteSlot(Long slotId) throws SlotNotFoundException;
}
