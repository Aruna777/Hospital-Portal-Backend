package com.BookAppointmentService.BookAppointmentService.dto;

import com.BookAppointmentService.BookAppointmentService.model.Booking;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {
    private Integer appointmentId;
    private Integer patientId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentReason;
    private String firstname;
    private String lastname;
    private String email;
    private String phonenumber;
    private Booking.Status status;
}
