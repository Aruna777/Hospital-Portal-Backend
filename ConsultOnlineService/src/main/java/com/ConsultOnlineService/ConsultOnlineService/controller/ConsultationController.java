package com.ConsultOnlineService.ConsultOnlineService.controller;

import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import com.ConsultOnlineService.ConsultOnlineService.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationRepository consultationRepository;

    @GetMapping
    public Flux<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Consultation>> getConsultationById(@PathVariable Integer id) {
        return consultationRepository.findByConsultationId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createConsultation(@RequestBody Consultation consultation) {
        return consultationRepository.save(consultation)
                .map(savedConsultation -> ResponseEntity.ok("Consultation booked successfully"));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateConsultation(@PathVariable Integer id, @RequestBody Consultation consultation) {
        consultation.setConsultationId(id);
        return consultationRepository.save(consultation)
                .map(updatedConsultation -> ResponseEntity.ok("Edited consultation successfully"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteConsultation(@PathVariable Integer id) {
        return consultationRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
