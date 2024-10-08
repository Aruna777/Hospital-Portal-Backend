package com.ConsultOnlineService.ConsultOnlineService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Table("consultations")
public class Consultation {
    @Id
    @Column("consultation_id")
    private Integer consultationId;

    @Column("user_id")
    private Integer userId;

    @Column("consultation_date")
    private LocalDate consultationDate;

    @Column("consultation_time")
    private LocalTime consultationTime;

    @Column("consultation_reason")
    private String consultationReason;

    @Column("consultation_status")
    private Status consultationStatus = Status.Pending;


    public enum Status {
        Pending,
        Completed
    }
}
