package com.unibooking.backend.user.controller;

import com.unibooking.backend.Exception.BookingNotFoundException; import com.unibooking.backend.Exception.SlotAlreadyBookedException; import com.unibooking.backend.user.dto.BookingDTO; import com.unibooking.backend.user.service.BookingService; import lombok.RequiredArgsConstructor; import org.springframework.http.HttpStatus; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/booking") @RequiredArgsConstructor public class BookingController {

    private final BookingService bookingService;

    //Create Booking
    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (SlotAlreadyBookedException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    //Get bookings by user
    @GetMapping("/user/{emailId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(@PathVariable String emailId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(emailId));
    }

    //Get bookings by provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(bookingService.getBookingsByProvider(providerId));
    }

    //Check slot availability
    @GetMapping("/slot/{slotId}/availability")
    public ResponseEntity<Boolean> isSlotAvailable(@PathVariable Long slotId) {
        return ResponseEntity.ok(bookingService.isSlotAvailable(slotId));
    }

    //Cancel booking
    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok("Booking cancelled successfully.");
        } catch (BookingNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    //Reschedule Booking
    @PutMapping("/reschedule/{bookingId}/{newSlotId}")
    public ResponseEntity<?> rescheduleBooking(@PathVariable Long bookingId, @PathVariable Long newSlotId) {
        try {
            BookingDTO updatedBooking = bookingService.rescheduleBooking(bookingId, newSlotId);
            return ResponseEntity.ok(updatedBooking);
        } catch (BookingNotFoundException | SlotAlreadyBookedException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    //Paginated bookings by user
    @GetMapping("/user/{emailId}/paginated")
    public ResponseEntity<List<BookingDTO>> getPaginatedBookingsByUser(
            @PathVariable String emailId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookingService.getPaginatedBookingsByUser(emailId, page, size));
    }

}