package com.unibooking.backend.user.controller;


import com.unibooking.backend.user.dto.BookingDTO;
import com.unibooking.backend.user.service.BookingService;
import com.unibooking.backend.user.service.ProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@AllArgsConstructor

public class BookingController {

    private final BookingService bookingService;
    private final ProviderService providerService;

    //Create booking (USER only)
    @PostMapping("/{slotId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingDTO> createBooking(
            @PathVariable Long slotId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(slotId, userDetails.getUsername()));
    }

    //Get my bookings (USER)
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDTO>> getMyBookings(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUser(userDetails.getUsername())
        );
    }

    //Get bookings for provider (PROVIDER)
    @GetMapping("/provider")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<BookingDTO>> getBookingsForProvider(
            @AuthenticationPrincipal UserDetails userDetails) {

        // assuming providerId is fetched from provider table using email
        Long providerId = providerService.getProviderIdByEmail(userDetails.getUsername());

        return ResponseEntity.ok(
                bookingService.getBookingsByProvider(providerId)
        );
    }

    //Cancel booking (USER only, ownership check)
    @PutMapping("/{bookingId}/cancel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails userDetails) {

        bookingService.cancelBooking(bookingId, userDetails.getUsername());
        return ResponseEntity.ok("Booking cancelled successfully.");
    }

    //Reschedule booking (USER)
    @PutMapping("/{bookingId}/reschedule/{newSlotId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingDTO> rescheduleBooking(
            @PathVariable Long bookingId,
            @PathVariable Long newSlotId,
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                bookingService.rescheduleBooking(bookingId, newSlotId, userDetails.getUsername())
        );
    }

    //Paginated bookings (USER)
    @GetMapping("/paginated")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingDTO>> getMyBookingsPaginated(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                bookingService.getPaginatedBookingsByUser(userDetails.getUsername(), page, size)
        );
    }

}
