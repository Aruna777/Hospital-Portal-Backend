package com.HealthCheckupService.HealthCheckupService.controller;

import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.HealthCheckupService.HealthCheckupService.mapper.CheckupMapper;
import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import com.HealthCheckupService.HealthCheckupService.repository.CheckupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CheckupControllerTest {

    private static final int CHECKUP_ID = 1;
    private static final int NOT_FOUND_ID = 99;

    @Mock
    private CheckupRepository checkupRepository;

    @Mock
    private CheckupMapper checkupMapper;

    @InjectMocks
    private CheckupController checkupController;

    private Checkup checkup;
    private CheckupDTO checkupDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        checkup = createCheckup();
        checkupDTO = createCheckupDTO();
    }

    private Checkup createCheckup() {
        Checkup checkup = new Checkup();
        checkup.setCheckupId(CHECKUP_ID);
        return checkup;
    }

    private CheckupDTO createCheckupDTO() {
        CheckupDTO checkupDTO = new CheckupDTO();
        checkupDTO.setCheckupId(CHECKUP_ID);
        return checkupDTO;
    }

    @Test
    public void testGetAllCheckups() {
        when(checkupRepository.findAll()).thenReturn(Flux.just(checkup));
        when(checkupMapper.toDTO(any(Checkup.class))).thenReturn(checkupDTO);

        StepVerifier.create(checkupController.getAllCheckups())
                .expectNext(checkupDTO)
                .verifyComplete();

        verify(checkupRepository, times(1)).findAll();
    }

    @Test
    public void testGetCheckupById_Found() {
        when(checkupRepository.findByCheckupId(CHECKUP_ID)).thenReturn(Mono.just(checkup));
        when(checkupMapper.toDTO(any(Checkup.class))).thenReturn(checkupDTO);

        Mono<ResponseEntity<CheckupDTO>> result = checkupController.getCheckupById(CHECKUP_ID);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                .verifyComplete();

        verify(checkupRepository, times(1)).findByCheckupId(CHECKUP_ID);
    }

    @Test
    public void testGetCheckupById_NotFound() {
        when(checkupRepository.findByCheckupId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<CheckupDTO>> result = checkupController.getCheckupById(NOT_FOUND_ID);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(checkupRepository, times(1)).findByCheckupId(NOT_FOUND_ID);
    }

    @Test
    public void testCreateCheckup_Success() {
        when(checkupMapper.toEntity(any(CheckupDTO.class))).thenReturn(checkup);
        when(checkupRepository.save(any(Checkup.class))).thenReturn(Mono.just(checkup));
        when(checkupMapper.toDTO(any(Checkup.class))).thenReturn(checkupDTO);

        Mono<ResponseEntity<CheckupDTO>> response = checkupController.createCheckup(checkupDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.CREATED && entity.getBody() != null)
                .verifyComplete();

        verify(checkupRepository, times(1)).save(any(Checkup.class));
    }

    @Test
    public void testCreateCheckup_Error() {
        when(checkupMapper.toEntity(any(CheckupDTO.class))).thenReturn(new Checkup());
        when(checkupRepository.save(any(Checkup.class))).thenReturn(Mono.error(new RuntimeException()));

        Mono<ResponseEntity<CheckupDTO>> response = checkupController.createCheckup(checkupDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.BAD_REQUEST && entity.getBody() == null)
                .verifyComplete();

        verify(checkupRepository, times(1)).save(any(Checkup.class));
    }

    @Test
    public void testUpdateCheckup_Success() {
        when(checkupRepository.findByCheckupId(CHECKUP_ID)).thenReturn(Mono.just(checkup));
        when(checkupMapper.toEntity(any(CheckupDTO.class))).thenReturn(checkup);
        when(checkupRepository.save(any(Checkup.class))).thenReturn(Mono.just(checkup));
        when(checkupMapper.toDTO(any(Checkup.class))).thenReturn(checkupDTO);

        Mono<ResponseEntity<CheckupDTO>> response = checkupController.updateCheckup(CHECKUP_ID, checkupDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody() != null)
                .verifyComplete();

        verify(checkupRepository, times(1)).findByCheckupId(CHECKUP_ID);
        verify(checkupRepository, times(1)).save(any(Checkup.class));
    }

    @Test
    public void testUpdateCheckup_NotFound() {
        when(checkupRepository.findByCheckupId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<CheckupDTO>> response = checkupController.updateCheckup(NOT_FOUND_ID, checkupDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is4xxClientError() && entity.getBody() == null)
                .verifyComplete();

        verify(checkupRepository, times(0)).save(any(Checkup.class));
    }

    @Test
    public void testDeleteCheckup_Success() {
        when(checkupRepository.deleteById(CHECKUP_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<String>> response = checkupController.deleteCheckup(CHECKUP_ID);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is2xxSuccessful() && entity.getBody().equals("Deleted successfully"))
                .verifyComplete();

        verify(checkupRepository, times(1)).deleteById(CHECKUP_ID);
    }

    @Test
    public void testDeleteCheckup_NotFound() {
        when(checkupRepository.deleteById(NOT_FOUND_ID)).thenReturn(Mono.error(new RuntimeException("Checkup not found")));

        Mono<ResponseEntity<String>> response = checkupController.deleteCheckup(NOT_FOUND_ID);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(checkupRepository, times(1)).deleteById(NOT_FOUND_ID);
    }

}
