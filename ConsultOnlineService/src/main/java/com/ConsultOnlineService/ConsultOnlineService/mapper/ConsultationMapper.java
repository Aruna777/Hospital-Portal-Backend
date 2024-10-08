package com.ConsultOnlineService.ConsultOnlineService.mapper;

import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {

    public ConsultationDTO toDto(Consultation consultation) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setConsultationId(consultation.getConsultationId());
        dto.setUserId(consultation.getUserId());
        dto.setConsultationDate(consultation.getConsultationDate());
        dto.setConsultationTime(consultation.getConsultationTime());
        dto.setConsultationReason(consultation.getConsultationReason());
        dto.setConsultationStatus(consultation.getConsultationStatus());
        return dto;
    }

    public Consultation toEntity(ConsultationDTO dto) {
        Consultation consultation = new Consultation();
        consultation.setConsultationId(dto.getConsultationId());
        consultation.setUserId(dto.getUserId());
        consultation.setConsultationDate(dto.getConsultationDate());
        consultation.setConsultationTime(dto.getConsultationTime());
        consultation.setConsultationReason(dto.getConsultationReason());
        consultation.setConsultationStatus(dto.getConsultationStatus());
        return consultation;
    }
}
