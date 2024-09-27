package com.BookAppointmentService.BookAppointmentService.controller;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.BookAppointmentService.BookAppointmentService.mapper.BookingMapper;
import com.BookAppointmentService.BookAppointmentService.model.Booking;
import com.BookAppointmentService.BookAppointmentService.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    private static final int APPOINTMENT_ID = 12;
    private static final int PATIENT_ID =7;
    private static final int NOT_FOUND_ID = 6;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingController bookingController;

    private Booking booking;
    private BookingDTO bookingDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        booking = createBooking();
        bookingDTO = createBookingDTO();
    }

    private Booking createBooking() {
        Booking booking = new Booking();
        booking.setAppointmentId(APPOINTMENT_ID);
        booking.setPatientId(PATIENT_ID);
        booking.setAppointmentDate(LocalDate.now());
        booking.setAppointmentTime(LocalTime.now());
        booking.setAppointmentReason("General Checkup");
        booking.setFirstname("John");
        booking.setLastname("Doe");
        booking.setEmail("john.doe@example.com");
        booking.setPhonenumber("1234567890");
        booking.setStatus(Booking.Status.PENDING);
        return booking;
    }

    private BookingDTO createBookingDTO() {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setAppointmentId(APPOINTMENT_ID);
        bookingDTO.setPatientId(PATIENT_ID);
        bookingDTO.setAppointmentDate(LocalDate.now());
        bookingDTO.setAppointmentTime(LocalTime.now());
        bookingDTO.setAppointmentReason("General Checkup");
        bookingDTO.setFirstname("John");
        bookingDTO.setLastname("Doe");
        bookingDTO.setEmail("john.doe@example.com");
        bookingDTO.setPhonenumber("1234567890");
        bookingDTO.setStatus(Booking.Status.PENDING);
        return bookingDTO;
    }

    @Test
    public void testGetBookingById_Found() {
        when(bookingRepository.findByAppointmentId(NOT_FOUND_ID)).thenReturn(Mono.just(booking));
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingDTO);

        verifyResponse(bookingController.getBookingById(NOT_FOUND_ID), HttpStatus.OK);
        verify(bookingRepository, times(1)).findByAppointmentId(NOT_FOUND_ID);
    }

    @Test
    public void testGetBookingById_NotFound() {
        when(bookingRepository.findByAppointmentId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<BookingDTO>> result = bookingController.getBookingById(NOT_FOUND_ID);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(bookingRepository, times(1)).findByAppointmentId(NOT_FOUND_ID);
    }
    private void verifyResponse(Mono<ResponseEntity<BookingDTO>> response, HttpStatus expectedStatus) {
        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().equals(expectedStatus) && entity.getBody() != null)
                .verifyComplete();
    }

    @Test
    public void testCreateBooking_Success() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.just(booking));
        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingDTO);

        verifyResponse(bookingController.createBooking(bookingDTO), HttpStatus.CREATED);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testCreateBooking_Error() {

        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.error(new RuntimeException()));
        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(new Booking());

        Mono<ResponseEntity<BookingDTO>> responseMono = bookingController.createBooking(bookingDTO);

        StepVerifier.create(responseMono)
                .expectNextMatches(response ->
                        response.getStatusCode() == HttpStatus.BAD_REQUEST && response.getBody() == null)
                .verifyComplete();

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
    @Test
    public void testUpdateBooking_Success() {

        when(bookingRepository.findByAppointmentId(NOT_FOUND_ID)).thenReturn(Mono.just(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(Mono.just(booking));
        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenReturn(bookingDTO);


        Mono<ResponseEntity<BookingDTO>> response = bookingController.updateBooking(NOT_FOUND_ID, bookingDTO);


        verifyResponse(response, HttpStatus.OK);
        verify(bookingRepository, times(1)).save(any(Booking.class));
        verify(bookingRepository, times(1)).findByAppointmentId(NOT_FOUND_ID);
    }

    @Test
    public void testUpdateBooking_NotFound() {
        when(bookingRepository.findByAppointmentId(NOT_FOUND_ID)).thenReturn(Mono.empty());

        Mono<ResponseEntity<BookingDTO>> response = bookingController.updateBooking(NOT_FOUND_ID, bookingDTO);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is4xxClientError() && entity.getBody() == null)
                .verifyComplete();

        verify(bookingRepository, times(0)).save(any(Booking.class));
    }

    @Test
    public void testDeleteBooking_Success() {
        when(bookingRepository.deleteById(anyInt())).thenReturn(Mono.empty());

        Mono<ResponseEntity<String>> response = bookingController.deleteBooking(6);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode().is2xxSuccessful() && entity.getBody().equals("Deleted successfully"))
                .verifyComplete();

        verify(bookingRepository, times(1)).deleteById(6);
    }

    @Test
    public void testDeleteBooking_NotFound() {

        when(bookingRepository.deleteById(anyInt())).thenReturn(Mono.error(new RuntimeException("Booking not found")));

        Mono<ResponseEntity<String>> response = bookingController.deleteBooking(2);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.NOT_FOUND)
                .verifyComplete();

        verify(bookingRepository, times(1)).deleteById(2);
    }

}
