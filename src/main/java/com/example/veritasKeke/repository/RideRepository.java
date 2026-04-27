package com.example.veritasKeke.repository;

import com.example.veritasKeke.entity.Ride;
import com.example.veritasKeke.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(RideStatus status);
}