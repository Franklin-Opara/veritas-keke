package com.example.veritasKeke.controller;

import com.example.veritasKeke.entity.Ride;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RideWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyRidersOfNewRequest(Ride ride) {
        messagingTemplate.convertAndSend("/topic/ride-requests", ride.getId());
    }

    public void notifyStudentRideAccepted(Ride ride) {
        messagingTemplate.convertAndSend(
                "/topic/ride-status/" + ride.getStudent().getId(),
                ride.getId()
        );
    }

    public void notifyStudentRideCompleted(Ride ride) {
        messagingTemplate.convertAndSend(
                "/topic/ride-completed/" + ride.getStudent().getId(),
                ride.getId()
        );
    }
}