package com.BookAppointmentService.BookAppointmentService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Table("Appointment")
public class Booking {

    @Id
    @Column("appointment_id")
    private Integer appointmentId;

    @Column("patient_id")
    private Integer patientId;

    @Column("appointment_date")
    private LocalDate appointmentDate;

    @Column("appointment_time")
    private LocalTime appointmentTime;

    @Column("status")
    private Status status = Status.PENDING;

    @Column("appointment_reason")
    private String appointmentReason;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("firstname")
    private String firstname;

    @Column("lastname")
    private String lastname;

    @Column("email")
    private String email;

    @Column("phonenumber")
    private String phonenumber;

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED
    }
}
