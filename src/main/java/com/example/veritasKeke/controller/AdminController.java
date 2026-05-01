package com.example.veritasKeke.controller;

import com.example.veritasKeke.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("totalStudents", adminService.getTotalStudents());
        model.addAttribute("totalRiders", adminService.getTotalRiders());
        model.addAttribute("totalRides", adminService.getTotalRides());
        return "admin/dashboard";
    }

    @GetMapping("/admin/students")
    public String students(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("students", adminService.getAllStudents());
        return "admin/students";
    }

    @GetMapping("/admin/riders")
    public String riders(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("riders", adminService.getAllRiders());
        return "admin/riders";
    }

    @PostMapping("/admin/delete-student")
    public String deleteStudent(@RequestParam Long studentId, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        adminService.deleteStudent(studentId);
        return "redirect:/admin/students";
    }

    @PostMapping("/admin/delete-rider")
    public String deleteRider(@RequestParam Long riderId, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        adminService.deleteRider(riderId);
        return "redirect:/admin/riders";
    }

}