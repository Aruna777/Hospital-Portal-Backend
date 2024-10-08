package com.ConsultOnlineService.ConsultOnlineService.dto;

import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ConsultationDTO {
    private Integer consultationId;
    private Integer userId;
    private LocalDate consultationDate;
    private LocalTime consultationTime;
    private String consultationReason;
    private Consultation.Status consultationStatus;
}