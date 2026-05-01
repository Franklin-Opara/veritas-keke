package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    public Rider getRiderByUserId(Long userId) {
        return riderRepository.findByUser_Id(userId).orElseThrow();
    }

    public void toggleAvailability(Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow();
        rider.setIsAvailable(!rider.getIsAvailable());
        riderRepository.save(rider);
    }

}