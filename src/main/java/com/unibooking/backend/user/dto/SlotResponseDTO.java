package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.SlotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class SlotResponseDTO {

    private Long slotId;
    private Long providerId;
    private LocalDate date;      // e.g. "2025-06-15"
    private LocalTime startTime; // e.g. "10:00"
    private LocalTime endTime;   // e.g. "10:30"

    private SlotStatus status;
    private boolean available;
    private LocalDateTime createdAt;

}