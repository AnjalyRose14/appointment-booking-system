package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.ProviderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderModel, Long> {

    // Find provider by email
    Optional<ProviderModel> findByProviderEmail(String providerEmail);

    // Filter providers by location
    List<ProviderModel> findByProviderLocation(String providerLocation);

    // Filter providers by category
    List<ProviderModel> findByProviderCategory(String providerCategory);

    boolean existsByProviderEmail(String email);

}
