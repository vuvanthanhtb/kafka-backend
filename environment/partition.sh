#!/bin/bash

docker exec kafka1 kafka-topics.sh --alter \
  --topic my-task-lag-topic \
  --bootstrap-server localhost:9092 \
  --partitions 10
