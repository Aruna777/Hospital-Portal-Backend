package com.aggregator.aggregator_service.mapper;


import com.BookAppointmentService.BookAppointmentService.dto.BookingDTO;
import com.ConsultOnlineService.ConsultOnlineService.dto.ConsultationDTO;
import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import com.LoginService.LoginService.dto.UserRegistrationDTO;
import com.aggregator.aggregator_service.dto.AggregatedResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AggregatorMapper {

    public AggregatedResponse toAggregatedResponse(UserRegistrationDTO userProfile,
                                                   List<BookingDTO> appointments,
                                                   List<ConsultationDTO> consultations,
                                                   List<CheckupDTO> checkups) {
        return new AggregatedResponse(userProfile, appointments, consultations, checkups);
    }
}

