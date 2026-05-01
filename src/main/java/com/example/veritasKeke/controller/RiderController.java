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
public class RiderController {

    private final RiderService riderService;
    private final RideService rideService;

    @GetMapping("/rider/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long riderId = (Long) session.getAttribute("riderId");
        if (riderId == null) return "redirect:/login";

        Rider rider = riderService.getRiderByUserId((Long) session.getAttribute("userId"));
        int pendingRequests = rideService.getSearchingRides().size();

        model.addAttribute("rider", rider);
        model.addAttribute("pendingRequests", pendingRequests);

        return "rider/dashboard";
    }

    @PostMapping("/rider/toggle-availability")
    public String toggleAvailability(HttpSession session) {
        Long riderId = (Long) session.getAttribute("riderId");
        if (riderId == null) return "redirect:/login";

        riderService.toggleAvailability(riderId);
        return "redirect:/rider/dashboard";
    }

    @GetMapping("/rider/requests")
    public String requests(HttpSession session, Model model) {
        Long riderId = (Long) session.getAttribute("riderId");
        if (riderId == null) return "redirect:/login";

        Rider rider = riderService.getRiderByUserId((Long) session.getAttribute("userId"));
        Ride activeRide = rideService.getActiveRideForRider(riderId);

        // Only block if unavailable AND no active ride
        if (!rider.getIsAvailable() && activeRide == null) {
            return "redirect:/rider/dashboard";
        }

        model.addAttribute("rider", rider);
        model.addAttribute("searchingRides", rideService.getSearchingRides());
        model.addAttribute("activeRide", activeRide);
        model.addAttribute("pendingRequests", rideService.getSearchingRides().size());

        return "rider/ride-requests";
    }

    @PostMapping("/rider/accept-ride")
    public String acceptRide(@RequestParam Long rideId, HttpSession session) {
        Long riderId = (Long) session.getAttribute("riderId");
        if (riderId == null) return "redirect:/login";

        Ride ride = rideService.acceptRide(rideId, riderId);

        if (ride == null) {
            return "redirect:/rider/requests?taken=true";
        }

        return "redirect:/rider/requests";
    }

    @PostMapping("/rider/complete-ride")
    public String completeRide(@RequestParam Long rideId, HttpSession session) {
        Long riderId = (Long) session.getAttribute("riderId");
        if (riderId == null) return "redirect:/login";

        rideService.completeRide(rideId);
        return "redirect:/rider/requests";
    }

    @GetMapping("/rider/profile")
    public String profile(HttpSession session, Model model) {
        if (session.getAttribute("riderId") == null) return "redirect:/login";

        Rider rider = riderService.getRiderByUserId((Long) session.getAttribute("userId"));
        model.addAttribute("rider", rider);
        model.addAttribute("pendingRequests", rideService.getSearchingRides().size());
        return "rider/profile";
    }
}