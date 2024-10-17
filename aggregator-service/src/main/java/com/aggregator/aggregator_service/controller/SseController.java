package com.aggregator.aggregator_service.controller;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SseController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/sse/checkup-updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamCheckupUpdates() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(emitter);
        });

        return emitter;
    }

    @RabbitListener(queues = "checkup-queue") // Specify your RabbitMQ queue name
    public void receiveMessage(String message) {
        for (SseEmitter emitter : emitters) {
            try {
                System.out.println("Received message: " + message);
                emitter.send(message);
            } catch (Exception e) {
                System.err.println("Failed to send message to emitter: " + e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
}
