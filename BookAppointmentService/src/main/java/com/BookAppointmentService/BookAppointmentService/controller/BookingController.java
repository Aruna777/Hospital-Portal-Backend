package com.BookAppointmentService.BookAppointmentService.controller;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.BookAppointmentService.BookAppointmentService.mapper.BookingMapper;
import com.BookAppointmentService.BookAppointmentService.model.Booking;
import com.BookAppointmentService.BookAppointmentService.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingController(BookingRepository bookingRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @GetMapping
    public Flux<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .map(bookingMapper::toDTO);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookingDTO>> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findByAppointmentId(id)
                .map(bookingMapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<BookingDTO>> createBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingRepository.save(bookingMapper.toEntity(bookingDTO))
                .map(savedBooking -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(bookingMapper.toDTO(savedBooking)))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BookingDTO>> updateBooking(@PathVariable Integer id, @RequestBody BookingDTO bookingDTO) {
        return bookingRepository.findByAppointmentId(id)
                .flatMap(existingBooking -> {
                    Booking booking = bookingMapper.toEntity(bookingDTO);
                    booking.setAppointmentId(id);
                    return bookingRepository.save(booking)
                            .map(updatedBooking -> ResponseEntity.ok(bookingMapper.toDTO(updatedBooking)));
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteBooking(@PathVariable Integer id) {
        return bookingRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.ok("Deleted successfully")))
                .onErrorReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking not found"));
    }
}
