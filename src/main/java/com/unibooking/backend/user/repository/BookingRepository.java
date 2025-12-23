package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.BookingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingModel, Long> {

    List<BookingModel> findByProvider_ProviderId(Long providerId);

    List<BookingModel> findByUser_UserEmail(String email);

    Page<BookingModel> findByUser_UserEmail(String email, Pageable pageable);

}
