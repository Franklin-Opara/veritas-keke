package com.example.veritasKeke.service;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.enums.Role;
import com.example.veritasKeke.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RiderRepository riderRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Student registerStudent(String firstName, String lastName, String email,
                                   String matricNumber, String phoneNumber, String password,
                                   String profilePhotoPath) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.STUDENT);
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        Student student = new Student();
        student.setUser(user);
        student.setMatricNumber(matricNumber);
        student.setPhoneNumber(phoneNumber);
        student.setProfilePhoto(profilePhotoPath);
        student.setTotalRides(0);
        return studentRepository.save(student);
    }

    public Rider registerRider(String firstName, String lastName, String email,
                               String phoneNumber, String plateNumber,
                               String bankName, String accountNumber, String password,
                               String profilePhotoPath) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.RIDER);
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);

        Rider rider = new Rider();
        rider.setUser(user);
        rider.setPhoneNumber(phoneNumber);
        rider.setPlateNumber(plateNumber);
        rider.setBankName(bankName);
        rider.setAccountNumber(accountNumber);
        rider.setProfilePhoto(profilePhotoPath);
        rider.setIsAvailable(false);
        rider.setTotalRides(0);
        return riderRepository.save(rider);
    }

    public User login(String identifier, String password, Role role) {
        User user = null;

        if (role == Role.STUDENT) {
            Student student = studentRepository.findByMatricNumber(identifier).orElse(null);
            if (student != null) user = student.getUser();
        } else {
            user = userRepository.findByEmail(identifier).orElse(null);
        }

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        if (user.getRole() != role) {
            return null;
        }

        return user;
    }
}
