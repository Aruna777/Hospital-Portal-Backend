package com.HealthCheckupService.HealthCheckupService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("checkup")
public class Checkup {
    @Id
    @Column("checkup_id")
    private Integer checkupId;

    @Column("patient_id")
    private Integer patientId;

    @Column("checkup_date")
    private LocalDate checkupDate;

    @Column("checkup_status")
    private CheckupStatus checkupStatus = CheckupStatus.PENDING;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    @Column("phonenumber")
    private String phonenumber;


    public enum CheckupStatus {
        COMPLETED,
        PENDING
    }
}

