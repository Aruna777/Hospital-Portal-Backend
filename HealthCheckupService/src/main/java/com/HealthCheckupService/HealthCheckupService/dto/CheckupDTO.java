package com.HealthCheckupService.HealthCheckupService.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CheckupDTO {
    private Integer checkupId;
    private Integer patientId;
    private LocalDate checkupDate;
    private String checkupStatus;
    private String firstname;
    private String lastname;
    private String phonenumber;
}
