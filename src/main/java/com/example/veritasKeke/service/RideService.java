package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.enums.RideStatus;
import com.example.veritasKeke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final StudentRepository studentRepository;
    private final RiderRepository riderRepository;

    public Ride requestRide(Long studentId, String pickupLocation, String destination) {
        Student student = studentRepository.findById(studentId).orElseThrow();

        int fare = calculateFare(destination);

        Ride ride = new Ride();
        ride.setStudent(student);
        ride.setPickupLocation(pickupLocation);
        ride.setDestination(destination);
        ride.setFare(fare);
        ride.setStatus(RideStatus.SEARCHING);
        ride.setCreatedAt(LocalDateTime.now());
        ride.setUpdatedAt(LocalDateTime.now());

        return rideRepository.save(ride);
    }

    public Ride acceptRide(Long rideId, Long riderId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();
        Rider rider = riderRepository.findById(riderId).orElseThrow();

        if (ride.getStatus() != RideStatus.SEARCHING) {
            return null; // ride already taken
        }

        ride.setRider(rider);
        ride.setStatus(RideStatus.ACCEPTED);
        ride.setUpdatedAt(LocalDateTime.now());

        rider.setIsAvailable(false);
        riderRepository.save(rider);

        return rideRepository.save(ride);
    }

    public Ride completeRide(Long rideId) {
        Ride ride = rideRepository.findById(rideId).orElseThrow();

        ride.setStatus(RideStatus.COMPLETED);
        ride.setUpdatedAt(LocalDateTime.now());

        Rider rider = ride.getRider();
        rider.setIsAvailable(true);
        rider.setTotalRides(rider.getTotalRides() + 1);
        riderRepository.save(rider);

        Student student = ride.getStudent();
        student.setTotalRides(student.getTotalRides() + 1);
        studentRepository.save(student);

        return rideRepository.save(ride);
    }

    public List<Ride> getSearchingRides() {
        return rideRepository.findByStatus(RideStatus.SEARCHING);
    }

    public List<Rider> getAvailableRiders() {
        return riderRepository.findByIsAvailableTrue();
    }

    private int calculateFare(String destination) {
        if (destination.equalsIgnoreCase("Hostel R") || destination.equalsIgnoreCase("Hostel S")) {
            return 200;
        }
        return 100;
    }
}
