package com.kafka_backend.speed;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @KafkaListener(topics = "my-task-topic-1", groupId = "myGroup")
    public void listenEvent(ConsumerRecord<String, String> record) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "my-task-topic", groupId = "myGroup", concurrency = "3")
    public void listenThreePartition(ConsumerRecord<String, String> record) {
        System.out.println("Partition: " + record.partition() + " -> " + record.value());
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Partition: " + record.partition() + ", complete: " + record.value() + " -> time taken: " + (System.currentTimeMillis() - startTime));
    }

    @KafkaListener(topics = "my-task-lag-topic", groupId = "myGroup", concurrency = "10")
    public void listenThreeLAG(ConsumerRecord<String, String> record, Acknowledgment ack) {
        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
            ack.acknowledge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Partition: " + record.partition() + ", complete: " + record.value() + " -> time taken: " + (System.currentTimeMillis() - startTime));
    }
}
