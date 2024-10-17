package com.HealthCheckupService.HealthCheckupService.controller;

import com.HealthCheckupService.HealthCheckupService.config.RabbitMQConfig;
import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.HealthCheckupService.HealthCheckupService.mapper.CheckupMapper;
import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import com.HealthCheckupService.HealthCheckupService.repository.CheckupRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/checkups")
public class CheckupController {

    private final CheckupRepository checkupRepository;
    private final CheckupMapper checkupMapper;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CheckupController(CheckupRepository checkupRepository, CheckupMapper checkupMapper, RabbitTemplate rabbitTemplate) {
        this.checkupRepository = checkupRepository;
        this.checkupMapper = checkupMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public Flux<CheckupDTO> getAllCheckups() {
        return checkupRepository.findAll()
                .map(checkupMapper::toDto);
    }

    @GetMapping("/user/{userId}")
    public Flux<CheckupDTO> getCheckupsByUserId(@PathVariable Integer userId) {
        return checkupRepository.findAllByUserId(userId)
                .map(checkupMapper::toDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CheckupDTO>> getCheckupById(@PathVariable Integer id) {
        return checkupRepository.findByCheckupId(id)
                .map(checkupMapper::toDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Mono<ResponseEntity<CheckupDTO>> createCheckup(@RequestBody CheckupDTO checkupDTO) {
        System.out.println("sent");
        Checkup checkup = checkupMapper.toEntity(checkupDTO);
        return checkupRepository.save(checkup)
                .map(savedCheckup -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(checkupMapper.toDto(savedCheckup));
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }


    @PutMapping("/{id}")
    public Mono<ResponseEntity<CheckupDTO>> updateCheckup(@PathVariable Integer id, @RequestBody CheckupDTO checkupDTO) {
        return checkupRepository.findByCheckupId(id)
                .flatMap(existingCheckup -> {
                    Checkup checkup = checkupMapper.toEntity(checkupDTO);
                    checkup.setCheckupId(id);
                    return checkupRepository.save(checkup)
                            .map(updatedCheckup -> {
                                // Create a message containing only checkup_id and checkup_status
                                Map<String, Object> message = new HashMap<>();
                                message.put("checkup_id", updatedCheckup.getCheckupId());
                                message.put("checkup_status", updatedCheckup.getCheckupStatus());

                                String jsonMessage = null;
                                try {
                                    jsonMessage = new ObjectMapper().writeValueAsString(message);
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, jsonMessage);

                                // Return the updated checkup as the response
                                CheckupDTO updatedCheckupDTO = checkupMapper.toDto(updatedCheckup);
                                return ResponseEntity.ok(updatedCheckupDTO);
                            });
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
