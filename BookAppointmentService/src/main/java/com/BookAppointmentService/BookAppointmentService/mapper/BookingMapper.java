package com.BookAppointmentService.BookAppointmentService.mapper;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.BookAppointmentService.BookAppointmentService.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setAppointmentId(booking.getAppointmentId());
        bookingDTO.setPatientId(booking.getPatientId());
        bookingDTO.setAppointmentDate(booking.getAppointmentDate());
        bookingDTO.setAppointmentTime(booking.getAppointmentTime());
        bookingDTO.setAppointmentReason(booking.getAppointmentReason());
        bookingDTO.setFirstname(booking.getFirstname());
        bookingDTO.setLastname(booking.getLastname());
        bookingDTO.setEmail(booking.getEmail());
        bookingDTO.setPhonenumber(booking.getPhonenumber());
        bookingDTO.setStatus(booking.getStatus());
        return bookingDTO;
    }


    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setAppointmentId(bookingDTO.getAppointmentId());
        booking.setPatientId(bookingDTO.getPatientId());
        booking.setAppointmentDate(bookingDTO.getAppointmentDate());
        booking.setAppointmentTime(bookingDTO.getAppointmentTime());
        booking.setAppointmentReason(bookingDTO.getAppointmentReason());
        booking.setFirstname(bookingDTO.getFirstname());
        booking.setLastname(bookingDTO.getLastname());
        booking.setEmail(bookingDTO.getEmail());
        booking.setPhonenumber(bookingDTO.getPhonenumber());
        booking.setStatus(bookingDTO.getStatus());
        return booking;
    }
}
