package com.HealthCheckupService.HealthCheckupService.controller;

import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.HealthCheckupService.HealthCheckupService.mapper.CheckupMapper;
import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import com.HealthCheckupService.HealthCheckupService.repository.CheckupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/checkups")
public class CheckupController {

    private final CheckupRepository checkupRepository;
    private final CheckupMapper checkupMapper;

    @Autowired
    public CheckupController(CheckupRepository checkupRepository, CheckupMapper checkupMapper) {
        this.checkupRepository = checkupRepository;
        this.checkupMapper = checkupMapper;
    }

    @GetMapping
    public Flux<CheckupDTO> getAllCheckups() {
        return checkupRepository.findAll()
                .map(checkupMapper::toDTO);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CheckupDTO>> getCheckupById(@PathVariable Integer id) {
        return checkupRepository.findByCheckupId(id)
                .map(checkupMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CheckupDTO>> createCheckup(@RequestBody CheckupDTO checkupDTO) {
        Checkup checkup = checkupMapper.toEntity(checkupDTO);
        return checkupRepository.save(checkup)
                .map(savedCheckup -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(checkupMapper.toDTO(savedCheckup)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CheckupDTO>> updateCheckup(@PathVariable Integer id, @RequestBody CheckupDTO checkupDTO) {
        return checkupRepository.findByCheckupId(id)
                .flatMap(existingCheckup -> {
                    Checkup checkup = checkupMapper.toEntity(checkupDTO);
                    checkup.setCheckupId(id);
                    return checkupRepository.save(checkup)
                            .map(updatedCheckup -> ResponseEntity.ok(checkupMapper.toDTO(updatedCheckup)));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteCheckup(@PathVariable Integer id) {
        return checkupRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
