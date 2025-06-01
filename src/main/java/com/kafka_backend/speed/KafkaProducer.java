package com.kafka_backend.speed;

import jakarta.annotation.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    public void executeTask1AndForget(String topic, String message) {
        System.out.println("Task1 has sent the request: " + message);
        long startTime = System.currentTimeMillis();
        kafkaTemplate.send(topic, message);
        System.out.println("Task2 is executed -> total time: " + (System.currentTimeMillis() - startTime));
    }

    public void executeTask1Sync(String topic, String message) throws ExecutionException, InterruptedException {
        System.out.println("Task1 has sent the request: " + message);
        long startTime = System.currentTimeMillis();
        kafkaTemplate.send(topic, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        SendResult<String, String> result = future.get(); // Execute and complete task1
        System.out.println("Execute and complete task1: " + result.getRecordMetadata().offset());
        System.out.println("Execute and complete task1 & Task2 is now being executed -> total time: " + (System.currentTimeMillis() - startTime));
    }

    public void executeTask1Async(String topic, String message) {
        System.out.println("Task1 has sent the request: " + message);
        long startTime = System.currentTimeMillis();
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.whenComplete((result, ex) -> {
            System.out.println("Producer: Task1 completed: " + message);
            System.out.println("Producer: Call task1 -> offset: " + result.getRecordMetadata().offset());
        });
        System.out.println("Task2 is executed -> total time: " + (System.currentTimeMillis() - startTime));
    }

    public void executePartition(String topic, String message) throws ExecutionException, InterruptedException {
        System.out.println("Task1 has sent the request: " + message);
        long startTime = System.currentTimeMillis();
        sendSyncOnePartition(topic, "task1");
        sendSyncOnePartition(topic, "task2");
        sendSyncOnePartition(topic, "task3");

        System.out.println("Time taken to complete all 3 tasks: " + (System.currentTimeMillis() - startTime));
    }

    private void sendSyncOnePartition(String topic, String task) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        kafkaTemplate.send(topic, task);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, task);
        SendResult<String, String> result = future.get(); // Execute and complete task
        System.out.println("Execute and complete: " + task + " -> time taken: " + (System.currentTimeMillis() - startTime));
    }

    public void executeLag(String topic, String message) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        kafkaTemplate.send(topic, message);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        SendResult<String, String> result = future.get(); // Execute and complete task
        System.out.println("Execute and complete: " + message + " -> time taken: " + (System.currentTimeMillis() - startTime));
    }
}
