package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.BookingNotFoundException;
import com.unibooking.backend.Exception.SlotAlreadyBookedException;
import com.unibooking.backend.user.dto.BookingDTO;
import com.unibooking.backend.user.model.BookingModel;
import com.unibooking.backend.user.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        if (!isSlotAvailable(bookingDTO.getSlotId())) {
            throw new SlotAlreadyBookedException("Slot is already booked: " + bookingDTO.getSlotId());
        }

        BookingModel booking = new BookingModel();
        booking.setEmailId(bookingDTO.getEmailId());
        booking.setProviderId(bookingDTO.getProviderId());
        booking.setSlotId(bookingDTO.getSlotId());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setBookingStatus("CONFIRMED");

        BookingModel savedBooking = bookingRepository.save(booking);

        return new BookingDTO(
                savedBooking.getBookingId(),
                savedBooking.getEmailId(),
                savedBooking.getProviderId(),
                savedBooking.getSlotId(),
                savedBooking.getBookingDate(),
                savedBooking.getBookingStatus()
        );
    }

    @Override
    public List<BookingDTO> getBookingsByUser(String emailId) {
        return bookingRepository.findByEmailId(emailId)
                .stream()
                .map(b -> new BookingDTO(
                        b.getBookingId(),
                        b.getEmailId(),
                        b.getProviderId(),
                        b.getSlotId(),
                        b.getBookingDate(),
                        b.getBookingStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> getBookingsByProvider(Long providerId) {
        return bookingRepository.findByProviderId(providerId)
                .stream()
                .map(b -> new BookingDTO(
                        b.getBookingId(),
                        b.getEmailId(),
                        b.getProviderId(),
                        b.getSlotId(),
                        b.getBookingDate(),
                        b.getBookingStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean isSlotAvailable(Long slotId) {
        return bookingRepository.findBySlotId(slotId).isEmpty();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + bookingId));

        booking.setBookingStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Override
    public BookingDTO rescheduleBooking(Long bookingId, Long newSlotId) {
        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found: " + bookingId));

        if (!isSlotAvailable(newSlotId)) {
            throw new SlotAlreadyBookedException("New slot is already booked: " + newSlotId);
        }

        booking.setSlotId(newSlotId);
        booking.setBookingStatus("RESCHEDULED");
        BookingModel updatedBooking = bookingRepository.save(booking);

        return new BookingDTO(
                updatedBooking.getBookingId(),
                updatedBooking.getEmailId(),
                updatedBooking.getProviderId(),
                updatedBooking.getSlotId(),
                updatedBooking.getBookingDate(),
                updatedBooking.getBookingStatus()
        );
    }

    @Override
    public List<BookingDTO> getPaginatedBookingsByUser(String emailId, int page, int size) {
        return bookingRepository.findByEmailId(emailId, PageRequest.of(page, size))
                .stream()
                .map(b -> new BookingDTO(
                        b.getBookingId(),
                        b.getEmailId(),
                        b.getProviderId(),
                        b.getSlotId(),
                        b.getBookingDate(),
                        b.getBookingStatus()
                ))
                .collect(Collectors.toList());
    }

}