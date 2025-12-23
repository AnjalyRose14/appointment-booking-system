package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.ProviderModel;
import com.unibooking.backend.user.model.SlotModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<SlotModel,Long> {

    List<SlotModel> findByProvider(ProviderModel provider);

}
