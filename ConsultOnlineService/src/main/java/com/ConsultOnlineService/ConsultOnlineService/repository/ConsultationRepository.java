package com.ConsultOnlineService.ConsultOnlineService.repository;

import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ConsultationRepository extends ReactiveCrudRepository<Consultation, Integer> {
    Mono<Consultation> findByConsultationId(Integer consultationId);
}