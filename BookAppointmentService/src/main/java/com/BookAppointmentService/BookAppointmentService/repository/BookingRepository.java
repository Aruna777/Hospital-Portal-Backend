package com.BookAppointmentService.BookAppointmentService.repository;

import com.BookAppointmentService.BookAppointmentService.model.Booking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepository  extends ReactiveCrudRepository<Booking, Integer> {
    Mono<Booking> findByAppointmentId(Integer appointmentId);
}