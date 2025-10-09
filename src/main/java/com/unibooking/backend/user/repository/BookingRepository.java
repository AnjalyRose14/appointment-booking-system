package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.BookingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingModel, Long> {

    // Existing methods
    List<BookingModel> findByEmailId(String emailId);
    List<BookingModel> findByProviderId(Long providerId);

    // New methods

    // Check if a slot is already booked
    List<BookingModel> findBySlotId(Long slotId);

    // Paginated bookings for a user
    Page<BookingModel> findByEmailId(String emailId, Pageable pageable);

    // Optional: Find booking by ID and status
    Optional<BookingModel> findByBookingIdAndBookingStatus(Long bookingId, String bookingStatus);
}
