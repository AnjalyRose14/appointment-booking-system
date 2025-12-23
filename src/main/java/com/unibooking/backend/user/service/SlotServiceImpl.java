package com.unibooking.backend.user.service;


import com.unibooking.backend.Exception.ProviderNotFoundException;
import com.unibooking.backend.Exception.SlotNotFoundException;
import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;
import com.unibooking.backend.user.model.ProviderModel;
import com.unibooking.backend.user.model.SlotModel;
import com.unibooking.backend.user.model.SlotStatus;
import com.unibooking.backend.user.repository.ProviderRepository;
import com.unibooking.backend.user.repository.SlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SlotServiceImpl implements SlotService {

    private final ProviderRepository providerRepository;
    private final SlotRepository slotRepository;

    @Override
    public SlotResponseDTO createSlot(String providerEmail, SlotCreateDTO dto) {

        ProviderModel provider = providerRepository
                .findByProviderEmail(providerEmail)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found"));

        SlotModel slot = SlotModel.builder()
                .provider(provider)
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .status(SlotStatus.AVAILABLE)
                .build();

        slotRepository.save(slot);
        return mapToDTO(slot);
    }

    @Override
    public SlotResponseDTO updateSlotStatus(
            Long slotId,
            String providerEmail,
            SlotUpdateDTO dto) {

        SlotModel slot = slotRepository.findById(slotId)
                .orElseThrow(() ->
                        new SlotNotFoundException("Slot not found"));

        // Ownership check
        if (!slot.getProvider().getProviderEmail().equals(providerEmail)) {
            throw new RuntimeException("You are not allowed to update this slot");
        }

        slot.setStatus(dto.getStatus());

        SlotModel saved = slotRepository.save(slot);

        return mapToDTO(slot);
    }

    @Override
    public void deleteSlot(Long slotId, String providerEmail) {

        SlotModel slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

        // Ownership check
        if (!slot.getProvider().getProviderEmail().equals(providerEmail)) {
            throw new RuntimeException("You are not allowed to delete this slot");
        }

        slotRepository.delete(slot);
    }

    @Override
    public List<SlotResponseDTO> getSlotsByProvider(Long providerId) {

        ProviderModel provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found"));

        return slotRepository
                .findByProvider(provider)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSlotAvailable(Long slotId) {

        SlotModel slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

        return slot.getStatus() == SlotStatus.AVAILABLE;
    }


    /* ===================== DTO MAPPER ===================== */

    private SlotResponseDTO mapToDTO(SlotModel slot) {

        return SlotResponseDTO.builder()
                .slotId(slot.getSlotId())
                .providerId(slot.getProvider().getProviderId())
                .date(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .createdAt(slot.getCreatedAt())
                .build();
    }
}

