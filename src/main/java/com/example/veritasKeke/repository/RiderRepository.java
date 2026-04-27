package com.example.veritasKeke.repository;

import com.example.veritasKeke.entity.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    List<Rider> findByIsAvailableTrue();
}