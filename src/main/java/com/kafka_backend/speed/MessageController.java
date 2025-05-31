package com.kafka_backend.speed;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Resource
    private KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        kafkaProducer.sendMessage("myTopic-002", message);
        return "Message sent to topic: " + message + " with value: " + message;
    }
}
