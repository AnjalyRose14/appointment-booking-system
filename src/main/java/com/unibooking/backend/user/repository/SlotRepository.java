package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.SlotModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<SlotModel,Long> {

    List<SlotModel> findByProviderId(Long providerId);

    List<SlotModel> findByStatus(String status);


}
