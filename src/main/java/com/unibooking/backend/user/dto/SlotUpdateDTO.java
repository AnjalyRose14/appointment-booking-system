package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.SlotStatus;
import lombok.Data;

@Data
public class SlotUpdateDTO {

    private Long slotId;
    private SlotStatus status; // AVAILABLE / BOOKED
    private boolean available;
}
