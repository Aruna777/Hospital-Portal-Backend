package com.ConsultOnlineService.ConsultOnlineService.controller;

import com.ConsultOnlineService.ConsultOnlineService.repository.ConsultationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsultationControllerTest {
    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private ConsultationController consultationController;


    @Test
    void getAllConsultations() {
    }

    @Test
    void getConsultationById() {
    }

    @Test
    void createConsultation() {
    }

    @Test
    void updateConsultation() {
    }

    @Test
    void deleteConsultation() {
    }
}