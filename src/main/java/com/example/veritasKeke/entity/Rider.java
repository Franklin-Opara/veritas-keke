package com.example.veritasKeke.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "riders")
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String phoneNumber;
    private String plateNumber;
    private String bankName;
    private String accountNumber;
    private String profilePhoto;
    private Boolean isAvailable;
    private Integer totalRides;
}