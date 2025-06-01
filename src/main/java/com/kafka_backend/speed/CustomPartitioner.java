package com.kafka_backend.speed;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class CustomPartitioner implements Partitioner {
    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(s);
        int numPartitions = partitionInfos.size();

        if (bytes == null) {
            int partition = ThreadLocalRandom.current().nextInt(numPartitions);
            System.out.println("Partition number " + partition);
            return partition;
        }
        return Math.abs(o.hashCode()) % numPartitions;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
