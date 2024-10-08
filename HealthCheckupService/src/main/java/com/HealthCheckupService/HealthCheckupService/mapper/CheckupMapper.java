package com.HealthCheckupService.HealthCheckupService.mapper;

import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import org.springframework.stereotype.Component;

@Component
public class CheckupMapper {

    public CheckupDTO toDto(Checkup checkup) {
        CheckupDTO dto = new CheckupDTO();
        dto.setCheckupId(checkup.getCheckupId());
        dto.setUserId(checkup.getUserId()); // Mapping userId
        dto.setCheckupDate(checkup.getCheckupDate());
        dto.setCheckupStatus(checkup.getCheckupStatus()); // Directly set enum
        return dto;
    }

    public Checkup toEntity(CheckupDTO dto) {
        Checkup checkup = new Checkup();
        checkup.setCheckupId(dto.getCheckupId());
        checkup.setUserId(dto.getUserId()); // Setting userId
        checkup.setCheckupDate(dto.getCheckupDate());
        checkup.setCheckupStatus(dto.getCheckupStatus()); // Directly set enum
        return checkup;
    }
}

