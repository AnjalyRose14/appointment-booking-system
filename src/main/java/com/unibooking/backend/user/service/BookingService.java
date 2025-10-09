package com.unibooking.backend.user.service;

import com.unibooking.backend.user.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO bookingDTO);

    List<BookingDTO> getBookingsByUser(String emailId);

    List<BookingDTO> getBookingsByProvider(Long providerId);

    Boolean isSlotAvailable(Long slotId);

    void cancelBooking(Long bookingId);

    BookingDTO rescheduleBooking(Long bookingId, Long newSlotId);

    List<BookingDTO> getPaginatedBookingsByUser(String emailId, int page, int size);
}