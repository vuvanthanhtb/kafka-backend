package com.kafka_backend.speed;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "myTopic-002", groupId = "myGroup")
    public void listenEvent(ConsumerRecord<String, String> record) {
        System.out.println("Received: " + record.value() +
                " on partition: " + record.partition() +
                " by consumer: " + record.topic() + "-" + record.partition() + "-" + Thread.currentThread().getId());
    }
}
