package com.kafka_backend.speed;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class MessageController {
    @Resource
    private KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        kafkaProducer.sendMessage("my-task-topic", message);
        return "Message sent to topic: my-task-topic with value: " + message;
    }

    @PostMapping("/task")
    public String executeTask1AndForget(@RequestBody String message) {
        // Note: Typically used when implementing logging
        kafkaProducer.executeTask1AndForget("my-task-topic", message);
        return "Message sent to topic: my-task-topic with value: " + message;
    }

    @PostMapping("/task/sync")
    public String executeTask1Sync(@RequestBody String message) throws ExecutionException, InterruptedException {
        kafkaProducer.executeTask1Sync("my-task-topic", message);
        return "Message sent to topic: my-task-topic with value: " + message;
    }

    @PostMapping("/task/async")
    public String executeTask1Async(@RequestBody String message) {
        kafkaProducer.executeTask1Async("my-task-topic", message);
        return "Message sent to topic: my-task-topic with value: " + message;
    }

    @PostMapping("/task/partition")
    public String executePartition(@RequestBody String message) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        kafkaProducer.executePartition("my-task-topic", message);
        long endTime = System.currentTimeMillis();
        return "Request processing time (1 partition): " + (endTime - startTime) + "ms";
    }

    @PostMapping("/task/lag")
    public String executeLag(@RequestBody String message) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        kafkaProducer.executeLag("my-task-lag-topic", message);
        long endTime = System.currentTimeMillis();
        return "Request processing time (1 partition): " + (endTime - startTime) + "ms";
    }
}
