package com.HealthCheckupService.HealthCheckupService.controller;

import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import com.HealthCheckupService.HealthCheckupService.repository.CheckupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/checkups")
public class CheckupController {

    @Autowired
    private CheckupRepository checkupRepository;

    @GetMapping
    public Flux<Checkup> getAllCheckups() {
        return checkupRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Checkup>> getCheckupById(@PathVariable Integer id) {
        return checkupRepository.findByCheckupId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createCheckup(@RequestBody Checkup checkup) {
        return checkupRepository.save(checkup)
                .map(savedCheckup -> ResponseEntity.ok("Checkup booked successfully"));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateCheckup(@PathVariable Integer id, @RequestBody Checkup checkup) {
        checkup.setCheckupId(id);
        return checkupRepository.save(checkup)
                .map(updatedCheckup -> ResponseEntity.ok("Edited checkup successfully"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteCheckup(@PathVariable Integer id) {
        return checkupRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}