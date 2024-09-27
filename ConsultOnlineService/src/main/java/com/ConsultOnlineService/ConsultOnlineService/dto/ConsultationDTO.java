package com.ConsultOnlineService.ConsultOnlineService.dto;

import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ConsultationDTO {
    private Integer consultationId;
    private LocalDate consultationDate;
    private LocalTime consultationStartTime;
    private LocalTime consultationEndTime;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String consultationReason;
    private Consultation.Status consultationStatus;
}
