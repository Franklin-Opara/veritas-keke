package com.example.veritasKeke.controller;

import com.example.veritasKeke.entity.*;
import com.example.veritasKeke.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final RideService rideService;

    @GetMapping("/student/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        Student student = studentService.getStudentByUserId(
                (Long) session.getAttribute("userId"));

        model.addAttribute("student", student);
        model.addAttribute("availableRidersCount", rideService.getAvailableRiders().size());

        return "student/dashboard";
    }

    @GetMapping("/student/riders")
    public String availableRiders(HttpSession session, Model model) {
        if (session.getAttribute("studentId") == null) return "redirect:/login";

        model.addAttribute("riders", rideService.getAvailableRiders());
        return "student/available-riders";
    }

    @GetMapping("/student/book-ride")
    public String bookRide(HttpSession session, Model model) {
        if (session.getAttribute("studentId") == null) return "redirect:/login";

        Long studentId = (Long) session.getAttribute("studentId");
        Ride activeRide = rideService.getActiveRideForStudent(studentId);

        if (activeRide != null) {
            model.addAttribute("activeRide", activeRide);
            model.addAttribute("hasActiveRide", true);
            return "student/book-ride";
        }

        return "student/book-ride";
    }

    @PostMapping("/student/book-ride")
    public String handleBookRide(
            @RequestParam String pickupLocation,
            @RequestParam String destination,
            HttpSession session,
            Model model) {

        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        Ride ride = rideService.requestRide(studentId, pickupLocation, destination);
        session.setAttribute("currentRideId", ride.getId());

        model.addAttribute("ride", ride);
        model.addAttribute("searching", true);
        return "student/book-ride";
    }

    @GetMapping("/student/profile")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("studentId") == null) return "redirect:/login";

        Student student = studentService.getStudentByUserId(
                (Long) session.getAttribute("userId"));

        model.addAttribute("student", student);
        return "student/profile";
    }

    @GetMapping("/student/ride-status")
    public String rideStatus(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        Ride ride = rideService.getActiveRideForStudent(studentId);

        if (ride == null) {
            return "redirect:/student/book-ride";
        }

        model.addAttribute("ride", ride);

        if (ride.getStatus().name().equals("ACCEPTED")) {
            return "student/ride-found";
        }

        model.addAttribute("searching", true);
        return "student/book-ride";
    }

    @GetMapping("/student/cancel-ride")
    public String cancelRide(HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        Ride activeRide = rideService.getActiveRideForStudent(studentId);
        if (activeRide != null) {
            rideService.cancelRide(activeRide.getId());
        }

        return "redirect:/student/book-ride";
    }
}