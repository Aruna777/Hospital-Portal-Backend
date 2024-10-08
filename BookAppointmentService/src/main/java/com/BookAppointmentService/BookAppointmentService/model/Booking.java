package com.BookAppointmentService.BookAppointmentService.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Table("appointments")
public class Booking {

    @Id
    @Column("appointment_id")
    private Integer appointmentId;

    @Column("user_id")
    private Integer userId;

    @Column("appointment_date")
    private LocalDate appointmentDate;

    @Column("appointment_time")
    private LocalTime appointmentTime;

    @Column("appointment_reason")
    private String appointmentReason;

    @Column("appointment_status")
    private Status status = Status.Pending;

    public enum Status {
        Pending,
        Confirmed,
        Cancelled
    }
}
