package com.unibooking.backend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")  // Optional: specify table name
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    @Column(unique = true, nullable = false)
    private String userEmail;
    private String userPhone;
    private String userPassword; //stored securely
    @CreationTimestamp
    private LocalDateTime createdAt;
    //private String role; // e.g., "USER", "PROVIDER", "ADMIN"
}