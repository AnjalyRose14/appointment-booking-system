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
    @Column(nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userPhone;
    @Column(nullable = false)
    private String userPassword; //stored securely in DB by hashing
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private boolean enabled = true;

}