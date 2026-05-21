package com.example.veritasKeke.repository;

import com.example.veritasKeke.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByMatricNumber(String matricNumber);
    Optional<Student> findByUser_Id(Long userId);
    boolean existsByMatricNumber(String matricNumber);
}
