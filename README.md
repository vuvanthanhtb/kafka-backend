### Cấu hình thêm partition cho Kafka topic
```shell
docker exec kafka1 kafka-topics.sh --alter \
  --topic myTopic-002 \
  --bootstrap-server localhost:9092 \
  --partitions 3
```