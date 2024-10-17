package com.HealthCheckupService.HealthCheckupService.repository;

import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CheckupRepository  extends ReactiveCrudRepository<Checkup, Integer> {
    Mono<Checkup> findByCheckupId(Integer id);
    Flux<Checkup> findAllByUserId(Integer userId);

}
