package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.enums.Role;
import com.example.veritasKeke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RiderRepository riderRepository;
    private final AdminRepository adminRepository;

    public void registerStudent(String firstName, String lastName, String email, String studentId, String phoneNumber, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.STUDENT);
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setMatricNumber(studentId);
        student.setPhoneNumber(phoneNumber);
        student.setTotalRides(0);
        studentRepository.save(student);
    }

    public void registerRider(String firstName, String lastName, String email, String phoneNumber, String kekePlateNumber, String bankName, String accountNumber, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.RIDER);
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        Rider rider = new Rider();
        rider.setUser(user);
        rider.setPhoneNumber(phoneNumber);
        rider.setPlateNumber(kekePlateNumber);
        rider.setBankName(bankName);
        rider.setAccountNumber(accountNumber);
        rider.setIsAvailable(false);
        rider.setTotalRides(0);
        riderRepository.save(rider);
    }

    public User login(String identifier, String password, Role role) {
        User user = userRepository.findByEmail(identifier).orElse(null);

        if (user == null || !user.getPassword().equals(password) || user.getRole() != role) {
            return null;
        }
        return user;
    }
}