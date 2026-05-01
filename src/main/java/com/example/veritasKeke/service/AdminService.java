package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.repository.*;
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

    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }

    public void deleteRider(Long riderId) {
        Rider rider = riderRepository.findById(riderId).orElseThrow();
        riderRepository.delete(rider);
        userRepository.delete(rider.getUser());
    }

    public long getTotalStudents() {
        return studentRepository.count();
    }

    public long getTotalRiders() {
        return riderRepository.count();
    }

    public long getTotalRides() {
        return rideRepository.count();
    }

}