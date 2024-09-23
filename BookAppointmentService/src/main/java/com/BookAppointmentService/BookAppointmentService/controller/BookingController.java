package com.BookAppointmentService.BookAppointmentService.controller;

import com.BookAppointmentService.BookAppointmentService.model.Booking;
import com.BookAppointmentService.BookAppointmentService.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Booking>> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findByAppointmentId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking)
                .map(savedBooking -> ResponseEntity.ok("Appointment booked successfully"))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateBooking(@PathVariable Integer id, @RequestBody Booking booking) {
        booking.setAppointmentId(id);
        return bookingRepository.save(booking)
                .map(updatedBooking -> ResponseEntity.ok("Edited appointment successfully"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteBooking(@PathVariable Integer id) {
        return bookingRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
