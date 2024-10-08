package com.aggregator.aggregator_service.service;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.aggregator.aggregator_service.dto.AggregatedResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AggregatorService {

    private final WebClient webClient;

    public AggregatorService(@LoadBalanced WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    private Mono<UserRegistrationDTO> fetchUserWithFallback(String url, UserRegistrationDTO fallback) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 404, clientResponse -> {
                    System.out.println("No active users found for URL: " + url);
                    return Mono.error(new RuntimeException("No active users found")); // Return an error to be handled later
                })
                .bodyToMono(UserRegistrationDTO.class)
                .onErrorResume(e -> {
                    System.err.println("Error fetching user data from " + url + ": " + e.getMessage());
                    return Mono.just(fallback);
                });
    }

    private <T> Mono<List<T>> fetchListWithFallback(String url, Class<T> clazz) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.value() == 404, clientResponse -> {
                    System.out.println("User has no active appointments for URL: " + url);
                    return Mono.empty();
                })
                .bodyToFlux(clazz)
                .collectList()
                .onErrorResume(e -> {
                    System.err.println("Error fetching list from " + url + ": " + e.getMessage());
                    return Mono.just(List.of());
                });
    }

    public Mono<AggregatedResponse> getPatientProfile(Integer user_id) {
        Mono<UserRegistrationDTO> profileMono = fetchUserWithFallback("http://LoginService/api/users/" + user_id, new UserRegistrationDTO());
        Mono<List<BookingDTO>> appointmentsMono = fetchListWithFallback("http://BookAppointmentService/api/bookings/" + user_id, BookingDTO.class);
        Mono<List<ConsultationDTO>> consultationsMono = fetchListWithFallback("http://ConsultOnlineService/api/consultations/" + user_id, ConsultationDTO.class);
        Mono<List<CheckupDTO>> checkupsMono = fetchListWithFallback("http://HealthCheckupService/api/checkups/" + user_id, CheckupDTO.class);

        return Mono.zip(profileMono, appointmentsMono, consultationsMono, checkupsMono)
                .map(tuple -> new AggregatedResponse(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4()));
    }

    public Flux<UserRegistrationDTO> fetchUsersWithAppointmentsAndCheckups() {
        Flux<Integer> userIdsFromAppointments = webClient.get()
                .uri("http://BookAppointmentService/api/bookings")
                .retrieve()
                .bodyToFlux(BookingDTO.class)
                .map(BookingDTO::getUserId);

        Flux<Integer> userIdsFromCheckups = webClient.get()
                .uri("http://HealthCheckupService/api/checkups")
                .retrieve()
                .bodyToFlux(CheckupDTO.class)
                .map(CheckupDTO::getUserId);

        Flux<Integer> combinedUserIds = Flux.merge(userIdsFromAppointments, userIdsFromCheckups).distinct();

        return combinedUserIds.collectList().flatMapMany(userIds ->
                Flux.fromIterable(userIds)
                        .flatMap(userId -> fetchUserWithFallback("http://LoginService/api/users/" + userId, null))
        );
    }
}
