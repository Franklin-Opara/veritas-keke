package com.example.veritasKeke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/student/dashboard")
    public String dashboard() {
        return "student/dashboard";
    }

    @GetMapping("/student/book-ride")
    public String bookRide() {
        return "student/book-ride";
    }

    @GetMapping("/student/riders")
    public String availableRiders() {
        return "student/available-riders";
    }

    @GetMapping("/student/profile")
    public String profile() {
        return "student/profile";
    }
}