package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getStudentByUserId(Long userId) {
        return studentRepository.findByUser_Id(userId).orElseThrow();
    }
}