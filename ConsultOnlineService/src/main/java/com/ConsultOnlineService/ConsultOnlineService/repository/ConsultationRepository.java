package com.ConsultOnlineService.ConsultOnlineService.repository;

import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConsultationRepository extends ReactiveCrudRepository<Consultation, Integer> {
    Flux<Consultation> findByLastname(String lastname); // Example of a custom query
    Mono<Consultation> findByConsultationId(Integer consultationId);
}