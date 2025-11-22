package com.unibooking.backend.user.dto;

import lombok.Data;

@Data
public class SlotUpdateDTO {

    private Long slotId;
    private String status; // AVAILABLE / BOOKED

}
