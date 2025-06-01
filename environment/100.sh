#!/bin/bash

for i in $(seq -f "%03g" 1 100); do
  curl -X POST -H "Content-Type: application/json" -d "{\"oder\": \"task-$i\"}" http://localhost:8888/task/lag
  echo "Sent order: task-$i"
done
