package com.unibooking.backend.user.repository;

import com.unibooking.backend.user.model.UserModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    // Find user by email (used for login or profile fetch)
    Optional<UserModel> findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);

    // You can add more custom query methods as needed
}