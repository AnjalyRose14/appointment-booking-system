package com.unibooking.backend.user.service;

import com.unibooking.backend.Exception.BookingNotFoundException;
import com.unibooking.backend.Exception.SlotAlreadyBookedException;
import com.unibooking.backend.Exception.SlotNotFoundException;
import com.unibooking.backend.Exception.UserNotFoundException;
import com.unibooking.backend.user.dto.BookingDTO;
import com.unibooking.backend.user.model.*;
import com.unibooking.backend.user.repository.BookingRepository;
import com.unibooking.backend.user.repository.SlotRepository;
import com.unibooking.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public BookingDTO createBooking(Long slotId, String userEmail) {

        UserModel user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        SlotModel slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException("Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new SlotAlreadyBookedException("Slot already booked");
        }

        ProviderModel provider = slot.getProvider();

        BookingModel booking = new BookingModel();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setProvider(provider);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        // update slot
        slot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(slot);

        return mapToDTO(booking);
    }

    @Override
    public List<BookingDTO> getBookingsByUser(String userEmail) {
        return bookingRepository.findByUser_UserEmail(userEmail)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<BookingDTO> getBookingsByProvider(Long providerId) {

        List<BookingModel> bookings =
                bookingRepository.findByProvider_ProviderId(providerId);

        return bookings.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void cancelBooking(Long bookingId, String userEmail) {

        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (!booking.getUser().getUserEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized cancellation");
        }

        SlotModel slot = booking.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);


        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
        slotRepository.save(slot);
    }

    @Override
    public BookingDTO rescheduleBooking(Long bookingId, Long newSlotId, String userEmail) {

        BookingModel booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        if (!booking.getUser().getUserEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized reschedule");
        }

        SlotModel newSlot = slotRepository.findById(newSlotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (newSlot.getStatus() != SlotStatus.AVAILABLE) {
            throw new SlotAlreadyBookedException("Slot already booked");
        }

        SlotModel oldSlot = booking.getSlot();
        oldSlot.setStatus(SlotStatus.AVAILABLE);

        booking.setSlot(newSlot);

        newSlot.setStatus(SlotStatus.BOOKED);


        slotRepository.save(oldSlot);
        slotRepository.save(newSlot);
        bookingRepository.save(booking);

        return mapToDTO(booking);
    }


    @Override
    public List<BookingDTO> getPaginatedBookingsByUser(String userEmail, int page, int size) {
        return bookingRepository
                .findByUser_UserEmail(userEmail, PageRequest.of(page, size))
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private BookingDTO mapToDTO(BookingModel booking) {
        BookingDTO dto = new BookingDTO();

        dto.setBookingId(booking.getBookingId());
        dto.setUserId(booking.getUser().getUserId());
        dto.setEmailId(booking.getUser().getUserEmail());
        dto.setSlotId(booking.getSlot().getSlotId());
        dto.setProviderId(booking.getProvider().getProviderId());
        dto.setBookingStatus(booking.getBookingStatus());
        dto.setBookedAt(booking.getBookedAt());
        return dto;
    }
}