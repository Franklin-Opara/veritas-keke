package com.example.veritasKeke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/students")
    public String students() {
        return "admin/students";
    }

    @GetMapping("/admin/riders")
    public String riders() {
        return "admin/riders";
    }
}