package com.aggregator.aggregator_service.dto;

import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.LoginService.LoginService.dto.UserRegistrationDTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedResponse {

    private UserRegistrationDTO userProfile;
    private List<BookingDTO> appointments;
    private List<ConsultationDTO> consultations;
    private List<CheckupDTO> checkups;

}
