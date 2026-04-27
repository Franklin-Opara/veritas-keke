package com.example.veritasKeke.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RiderController {

    @GetMapping("/rider/dashboard")
    public String dashboard() {
        return "rider/dashboard";
    }

    @GetMapping("/rider/requests")
    public String requests() {
        return "rider/ride-requests";
    }

    @GetMapping("/rider/profile")
    public String profile() {
        return "rider/profile";
    }
}