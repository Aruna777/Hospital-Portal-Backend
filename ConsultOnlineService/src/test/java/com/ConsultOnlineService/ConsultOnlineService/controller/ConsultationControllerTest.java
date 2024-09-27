package com.ConsultOnlineService.ConsultOnlineService.controller;

import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.ConsultOnlineService.ConsultOnlineService.mapper.ConsultationMapper;
import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import com.ConsultOnlineService.ConsultOnlineService.repository.ConsultationRepository;
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

public class ConsultationControllerTest {

    private static final int CONSULTATION_ID = 1;
    private static final int NOT_FOUND_ID = 99;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ConsultationMapper consultationMapper;

    @InjectMocks
    private ConsultationController consultationController;

    private Consultation consultation;
    private ConsultationDTO consultationDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        consultation = createConsultation();
        consultationDTO = createConsultationDTO();
    }

    private Consultation createConsultation() {
        Consultation consultation = new Consultation();
        consultation.setConsultationId(CONSULTATION_ID);
        return consultation;
    }

    private ConsultationDTO createConsultationDTO() {
        ConsultationDTO consultationDTO = new ConsultationDTO();
        consultationDTO.setConsultationId(CONSULTATION_ID);
        return consultationDTO;
    }

    @Test
    public void testGetAllConsultations() {
        when(consultationRepository.findAll()).thenReturn(Flux.just(consultation));
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDTO);

        StepVerifier.create(consultationController.getAllConsultations())
                .expectNext(consultationDTO)
                .verifyComplete();

        verify(consultationRepository, times(1)).findAll();
    }

    @Test
    public void testGetConsultationById_Found() {
        when(consultationRepository.findByConsultationId(CONSULTATION_ID)).thenReturn(Mono.just(consultation));
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDTO);

        Mono<ResponseEntity<ConsultationDTO>> result = consultationController.getConsultationById(CONSULTATION_ID);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK && response.getBody() != null)
                .verifyComplete();

        verify(consultationRepository, times(1)).findByConsultationId(CONSULTATION_ID);
    }

    @Test
    public void testGetConsultationById_NotFound() {
        when(consultationRepository.findByConsultationId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<ConsultationDTO>> result = consultationController.getConsultationById(NOT_FOUND_ID);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(consultationRepository, times(1)).findByConsultationId(NOT_FOUND_ID);
    }

    @Test
    public void testCreateConsultation_Success() {
        when(consultationMapper.toEntity(any(ConsultationDTO.class))).thenReturn(consultation);
        when(consultationRepository.save(any(Consultation.class))).thenReturn(Mono.just(consultation));
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDTO);

        Mono<ResponseEntity<ConsultationDTO>> response = consultationController.createConsultation(consultationDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.CREATED && entity.getBody() != null)
                .verifyComplete();

        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    public void testCreateConsultation_Error() {
        when(consultationMapper.toEntity(any(ConsultationDTO.class))).thenReturn(new Consultation());
        when(consultationRepository.save(any(Consultation.class))).thenReturn(Mono.error(new RuntimeException()));

        Mono<ResponseEntity<ConsultationDTO>> response = consultationController.createConsultation(consultationDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.BAD_REQUEST && entity.getBody() == null)
                .verifyComplete();

        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    public void testUpdateConsultation_Success() {
        when(consultationRepository.findByConsultationId(CONSULTATION_ID)).thenReturn(Mono.just(consultation));
        when(consultationMapper.toEntity(any(ConsultationDTO.class))).thenReturn(consultation);
        when(consultationRepository.save(any(Consultation.class))).thenReturn(Mono.just(consultation));
        when(consultationMapper.toDto(any(Consultation.class))).thenReturn(consultationDTO);

        Mono<ResponseEntity<ConsultationDTO>> response = consultationController.updateConsultation(CONSULTATION_ID, consultationDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody() != null)
                .verifyComplete();

        verify(consultationRepository, times(1)).findByConsultationId(CONSULTATION_ID);

        verify(consultationRepository, times(1)).save(any(Consultation.class));
    }

    @Test
    public void testUpdateConsultation_NotFound() {
        when(consultationRepository.findByConsultationId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<ConsultationDTO>> response = consultationController.updateConsultation(NOT_FOUND_ID, consultationDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is4xxClientError() && entity.getBody() == null)
                .verifyComplete();

        verify(consultationRepository, times(0)).save(any(Consultation.class));
    }

    @Test
    public void testDeleteConsultation_Success() {
        when(consultationRepository.deleteById(CONSULTATION_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<String>> response = consultationController.deleteConsultation(CONSULTATION_ID);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is2xxSuccessful() && entity.getBody().equals("Deleted successfully"))
                .verifyComplete();

        verify(consultationRepository, times(1)).deleteById(CONSULTATION_ID);
    }

    @Test
    public void testDeleteConsultation_NotFound() {
        when(consultationRepository.deleteById(NOT_FOUND_ID)).thenReturn(Mono.error(new RuntimeException("Consultation not found")));

        Mono<ResponseEntity<String>> response = consultationController.deleteConsultation(NOT_FOUND_ID);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(consultationRepository, times(1)).deleteById(NOT_FOUND_ID);
    }

}
