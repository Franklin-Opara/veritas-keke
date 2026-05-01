package com.example.veritasKeke.controller;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.enums.Role;
import com.example.veritasKeke.service.AuthService;
import com.example.veritasKeke.service.RiderService;
import com.example.veritasKeke.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final StudentService studentService;
    private final RiderService riderService;

    private final com.example.veritasKeke.repository.UserRepository userRepository;
    private final com.example.veritasKeke.repository.AdminRepository adminRepository;

    @GetMapping({"/", "/landing"})
    public String landing() {
        return "auth/landing";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam String role,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            @RequestParam(required = false) String matricNumber,
            @RequestParam(required = false) String plateNumber,
            @RequestParam(required = false) String bankName,
            @RequestParam(required = false) String accountNumber) {

        if (role.equals("student")) {
            authService.registerStudent(firstName, lastName, email,
                    matricNumber, phoneNumber, password);
        } else if (role.equals("rider")) {
            authService.registerRider(firstName, lastName, email,
                    phoneNumber, plateNumber, bankName, accountNumber, password);
        }

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String identifier,
            @RequestParam String password,
            @RequestParam String role,
            HttpSession session) {

        Role userRole = Role.valueOf(role.toUpperCase());
        User user = authService.login(identifier, password, userRole);

        if (user == null) {
            return "redirect:/login?error=true";
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("role", user.getRole().name());

        if (user.getRole() == Role.STUDENT) {
            Student student = studentService.getStudentByUserId(user.getId());
            session.setAttribute("studentId", student.getId());
            return "redirect:/student/dashboard";
        } else if (user.getRole() == Role.RIDER) {
            Rider rider = riderService.getRiderByUserId(user.getId());
            session.setAttribute("riderId", rider.getId());
            return "redirect:/rider/dashboard";
        } else {
            return "redirect:/admin/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/landing";
    }


}