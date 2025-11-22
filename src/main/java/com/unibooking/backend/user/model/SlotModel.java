package com.unibooking.backend.user.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name= "slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    private Long providerId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status; // AVAILABLE / BOOKED

    private LocalDateTime createdAt;


}
