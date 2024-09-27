package com.ConsultOnlineService.ConsultOnlineService.mapper;

import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.ConsultOnlineService.ConsultOnlineService.model.Consultation;
import org.springframework.stereotype.Component;

@Component
public class ConsultationMapper {


    public ConsultationDTO toDto(Consultation consultation) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setConsultationId(consultation.getConsultationId());
        dto.setConsultationDate(consultation.getConsultationDate());
        dto.setConsultationStartTime(consultation.getConsultationStartTime());
        dto.setConsultationEndTime(consultation.getConsultationEndTime());
        dto.setFirstname(consultation.getFirstname());
        dto.setLastname(consultation.getLastname());
        dto.setPhonenumber(consultation.getPhonenumber());
        dto.setConsultationReason(consultation.getConsultationReason());
        dto.setConsultationStatus(consultation.getConsultationStatus());
        return dto;
    }


    public Consultation toEntity(ConsultationDTO dto) {
        Consultation consultation = new Consultation();
        consultation.setConsultationId(dto.getConsultationId());
        consultation.setConsultationDate(dto.getConsultationDate());
        consultation.setConsultationStartTime(dto.getConsultationStartTime());
        consultation.setConsultationEndTime(dto.getConsultationEndTime());
        consultation.setFirstname(dto.getFirstname());
        consultation.setLastname(dto.getLastname());
        consultation.setPhonenumber(dto.getPhonenumber());
        consultation.setConsultationReason(dto.getConsultationReason());
        consultation.setConsultationStatus(dto.getConsultationStatus());
        return consultation;
    }
}
