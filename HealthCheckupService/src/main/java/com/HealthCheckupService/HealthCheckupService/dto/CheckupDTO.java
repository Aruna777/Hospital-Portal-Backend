package com.HealthCheckupService.HealthCheckupService.dto;

import com.HealthCheckupService.HealthCheckupService.model.Checkup;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckupDTO {
    private Integer checkupId;
    private Integer userId;
    private LocalDate checkupDate;
    private Checkup.CheckupStatus checkupStatus;
}
