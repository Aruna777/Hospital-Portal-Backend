package com.HealthCheckupService.HealthCheckupService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("checkups") // Updated table name
public class Checkup {
    @Id
    @Column("checkup_id")
    private Integer checkupId;

    @Column("user_id")
    private Integer userId;

    @Column("checkup_date")
    private LocalDate checkupDate;

    @Column("checkup_status")
    private CheckupStatus checkupStatus = CheckupStatus.Pending;

    public enum CheckupStatus {
        Completed,
        Pending
    }
}

