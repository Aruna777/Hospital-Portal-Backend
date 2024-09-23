package com.ConsultOnlineService.ConsultOnlineService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Table("consultation")
public class Consultation {
    @Id
    @Column("consultation_id")
    private Integer consultationId;

    @Column("consultation_date")
    private LocalDate consultationDate;

    @Column("consultation_start_time")
    private LocalTime consultationStartTime;

    @Column("consultation_end_time")
    private LocalTime consultationEndTime;

    @Column("consultation_status")
    private Status consultationStatus = Status.PENDING; // Use enum for status

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    @Column("phonenumber")
    private String phonenumber;

    @Column("consultation_reason")
    private String consultationReason;

    public enum Status {
        PENDING,
        COMPLETED
    }
}

