package com.BookAppointmentService.BookAppointmentService.dto;

import com.BookAppointmentService.BookAppointmentService.model.Booking;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {
    private Integer appointmentId;
    private Integer  userId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentReason;
    private Booking.Status status;
}
