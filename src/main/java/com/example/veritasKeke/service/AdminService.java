package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.enums.RideStatus;
import com.example.veritasKeke.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepository studentRepository;
    private final RiderRepository riderRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Rider> getAllRiders() {
        return riderRepository.findAll();
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        rideRepository.deleteAll(rideRepository.findByStudent(student));
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }

    @Transactional
    public void deleteRider(Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow();
        List<Ride> riderRides = rideRepository.findByRider(rider);
        riderRides.forEach(ride -> ride.setRider(null));
        rideRepository.saveAll(riderRides);
        riderRepository.delete(rider);
        userRepository.delete(rider.getUser());
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public long getTotalRiders() {
        return riderRepository.count();
    }

    public long getAvailableRiders() {
        return riderRepository.findByIsAvailableTrue().size();
    }

    public long getBusyRiders() {
        return riderRepository.findAll().stream()
                .filter(rider -> rideRepository.findByRiderAndStatus(rider, RideStatus.ACCEPTED).isPresent())
                .count();
    }

    public long getOfflineRiders() {
        long totalRiders = getTotalRiders();
        long availableRiders = getAvailableRiders();
        long busyRiders = getBusyRiders();
        return totalRiders - availableRiders - busyRiders;
    }

    public long getTotalRides() {
        return rideRepository.count();
    }

}
