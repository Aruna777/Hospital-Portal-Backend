package com.ConsultOnlineService.ConsultOnlineService.controller;

import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.ConsultOnlineService.ConsultOnlineService.mapper.ConsultationMapper;
import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import com.ConsultOnlineService.ConsultOnlineService.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMapper consultationMapper;

    @Autowired
    public ConsultationController(ConsultationRepository consultationRepository, ConsultationMapper consultationMapper) {
        this.consultationRepository = consultationRepository;
        this.consultationMapper = consultationMapper;
    }

    @GetMapping
    public Flux<ConsultationDTO> getAllConsultations() {
        return consultationRepository.findAll()
                .map(consultationMapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ConsultationDTO>> getConsultationById(@PathVariable Integer id) {
        return consultationRepository.findByConsultationId(id)
                .map(consultationMapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ConsultationDTO>> createConsultation(@RequestBody ConsultationDTO consultationDTO) {
        Consultation consultation = consultationMapper.toEntity(consultationDTO);
        return consultationRepository.save(consultation)
                .map(savedConsultation -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(consultationMapper.toDto(savedConsultation)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ConsultationDTO>> updateConsultation(@PathVariable Integer id, @RequestBody ConsultationDTO consultationDTO) {
        Consultation consultation = consultationMapper.toEntity(consultationDTO);
        consultation.setConsultationId(id);
        return consultationRepository.save(consultation)
                .map(updatedConsultation -> ResponseEntity.ok(consultationMapper.toDto(updatedConsultation)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteConsultation(@PathVariable Integer id) {
        return consultationRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
