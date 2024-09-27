package com.HealthCheckupService.HealthCheckupService.mapper;

import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import org.springframework.stereotype.Component;

@Component
public class CheckupMapper {

    public CheckupDTO toDTO(Checkup checkup) {
        CheckupDTO dto = new CheckupDTO();
        dto.setCheckupId(checkup.getCheckupId());
        dto.setPatientId(checkup.getPatientId());
        dto.setCheckupDate(checkup.getCheckupDate());
        dto.setCheckupStatus(checkup.getCheckupStatus().name());
        dto.setFirstname(checkup.getFirstname());
        dto.setLastname(checkup.getLastname());
        dto.setPhonenumber(checkup.getPhonenumber());
        return dto;
    }

    public Checkup toEntity(CheckupDTO dto) {
        Checkup checkup = new Checkup();
        checkup.setCheckupId(dto.getCheckupId());
        checkup.setPatientId(dto.getPatientId());
        checkup.setCheckupDate(dto.getCheckupDate());
        checkup.setCheckupStatus(Checkup.CheckupStatus.valueOf(dto.getCheckupStatus()));
        checkup.setFirstname(dto.getFirstname());
        checkup.setLastname(dto.getLastname());
        checkup.setPhonenumber(dto.getPhonenumber());
        return checkup;
    }
}
