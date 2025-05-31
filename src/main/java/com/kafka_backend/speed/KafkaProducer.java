package com.kafka_backend.speed;

import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        for (int i = 0; i < 3; i++) {
            kafkaTemplate.send(topic, message);
        }

        System.out.println("Message sent to topic: " + topic + " with value: " + message);
    }
}
