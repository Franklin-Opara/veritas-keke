package com.example.veritasKeke.repository;

import com.example.veritasKeke.entity.Ride;
import com.example.veritasKeke.entity.Rider;
import com.example.veritasKeke.entity.Student;
import com.example.veritasKeke.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByStatus(RideStatus status);

    Optional<Ride> findByStudentAndStatusIn(Student student, List<RideStatus> statuses);
    Optional<Ride> findByRiderAndStatus(Rider rider, RideStatus status);
}