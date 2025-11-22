package com.unibooking.backend.user.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SlotCreateDTO {

    private Long providerId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
