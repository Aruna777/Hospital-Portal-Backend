package com.BookAppointmentService.BookAppointmentService.mapper;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.BookAppointmentService.BookAppointmentService.model.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

    public BookingDTO toDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setAppointmentId(booking.getAppointmentId());
        bookingDTO.setUserId(booking.getUserId());
        bookingDTO.setAppointmentDate(booking.getAppointmentDate());
        bookingDTO.setAppointmentTime(booking.getAppointmentTime());
        bookingDTO.setAppointmentReason(booking.getAppointmentReason());
        bookingDTO.setStatus(booking.getStatus());
        return bookingDTO;
    }

    public Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setAppointmentId(bookingDTO.getAppointmentId());
        booking.setUserId(bookingDTO.getUserId());
        booking.setAppointmentDate(bookingDTO.getAppointmentDate());
        booking.setAppointmentTime(bookingDTO.getAppointmentTime());
        booking.setAppointmentReason(bookingDTO.getAppointmentReason());
        booking.setStatus(bookingDTO.getStatus());
        return booking;
    }
}
