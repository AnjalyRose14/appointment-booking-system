package com.unibooking.backend.user.service;

import com.unibooking.backend.user.dto.BookingDTO;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(Long slotId, String userEmail);

    List<BookingDTO> getBookingsByUser(String userEmail);

    List<BookingDTO> getBookingsByProvider(Long providerId);

    void cancelBooking(Long bookingId, String userEmail);

    BookingDTO rescheduleBooking(Long bookingId, Long newSlotId, String userEmail);

    List<BookingDTO> getPaginatedBookingsByUser(String userEmail, int page, int size);
}
