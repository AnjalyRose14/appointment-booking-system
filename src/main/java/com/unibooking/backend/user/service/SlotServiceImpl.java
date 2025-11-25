package com.unibooking.backend.user.service;


import com.unibooking.backend.Exception.SlotNotFoundException;
import com.unibooking.backend.user.dto.SlotCreateDTO;
import com.unibooking.backend.user.dto.SlotResponseDTO;
import com.unibooking.backend.user.dto.SlotUpdateDTO;
import com.unibooking.backend.user.model.SlotModel;
import com.unibooking.backend.user.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;

    @Override
    public SlotResponseDTO createSlot(SlotCreateDTO createDTO) {

        SlotModel slot = SlotModel.builder()
                .providerId(createDTO.getProviderId())
                .date(createDTO.getDate())
                .startTime(createDTO.getStartTime())
                .endTime(createDTO.getEndTime())
                .status("AVAILABLE")
                .createdAt(LocalDateTime.now())
                .build();

        SlotModel saved = slotRepository.save(slot);
        return mapToResponse(saved);
    }

    @Override
    public SlotResponseDTO updateSlotStatus(SlotUpdateDTO updateDTO) throws SlotNotFoundException {
        SlotModel slot = slotRepository.findById(updateDTO.getSlotId())
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + updateDTO.getSlotId()));

        slot.setStatus(updateDTO.getStatus());
        return mapToResponse(slotRepository.save(slot));
    }

    @Override
    public List<SlotResponseDTO> getSlotsByProvider(Long providerId) {
        return slotRepository.findByProviderId(providerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isSlotAvailable(Long slotId) {
        return slotRepository.findById(slotId)
                .map(slot -> slot.getStatus().equals("AVAILABLE"))
                .orElse(false);
    }

    @Override
    public void deleteSlot(Long slotId) throws SlotNotFoundException {
        SlotModel slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found with id: " + slotId));

        slotRepository.delete(slot);
    }

    private SlotResponseDTO mapToResponse(SlotModel slot) {
        return new SlotResponseDTO(
                    slot.getSlotId(),
                    slot.getProviderId(),
                    slot.getDate(),
                    slot.getStartTime(),
                    slot.getEndTime(),
                    slot.getStatus(),
                    slot.getCreatedAt()
        );
    }
}
