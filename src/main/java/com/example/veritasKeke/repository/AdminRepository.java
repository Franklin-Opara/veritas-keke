package com.example.veritasKeke.repository;

import com.example.veritasKeke.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}