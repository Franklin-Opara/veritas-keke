package com.example.veritasKeke.entity;

import com.example.veritasKeke.enums.RideStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    private String pickupLocation;
    private String destination;
    private Integer fare;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}