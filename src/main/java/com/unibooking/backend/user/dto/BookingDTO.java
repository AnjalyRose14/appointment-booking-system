package com.unibooking.backend.user.dto;

import com.unibooking.backend.user.model.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long bookingId;
    private Long userId;
    private String emailId;
    private Long slotId;
    private Long providerId;
    private BookingStatus bookingStatus;
    private LocalDateTime bookedAt;

}
