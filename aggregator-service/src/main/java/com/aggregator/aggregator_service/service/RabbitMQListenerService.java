package com.aggregator.aggregator_service.service;

import com.HealthCheckupService.HealthCheckupService.config.RabbitMQConfig;
import com.HealthCheckupService.HealthCheckupService.dto.CheckupDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListenerService {

    private final SseService sseService;

    public RabbitMQListenerService(SseService sseService) {
        this.sseService = sseService;
    }

    @RabbitListener(queues = "checkup-queue")
    public void listenForCheckupUpdate(CheckupDTO checkup) {
        sseService.sendCheckupUpdate(checkup);
    }
}

