package com.aggregator.aggregator_service.controller;

import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.aggregator.aggregator_service.dto.AggregatedResponse;
import com.aggregator.aggregator_service.service.AggregatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/aggregate")
public class AggregatorController {

    private final AggregatorService aggregatorService;

    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @GetMapping("/profiles/{user_id}")
    public Mono<AggregatedResponse> getPatientProfile(@PathVariable Integer user_id) {
        return aggregatorService.getPatientProfile(user_id)
                .onErrorResume(e -> {
                    System.err.println("Error fetching patient profile: " + e.getMessage());
                    return Mono.error(new RuntimeException("Could not fetch patient profile, please try again later."));
                });
    }

    @GetMapping("/users/appointments-checkups")
    public Flux<UserRegistrationDTO> getUsersWithAppointmentsAndCheckups() {
        return aggregatorService.fetchUsersWithAppointmentsAndCheckups()
                .onErrorResume(e -> {
                    System.err.println("Error fetching users with appointments and checkups: " + e.getMessage());
                    return Flux.empty();
                });
    }
}


