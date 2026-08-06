#!/usr/bin/env bash
docker compose stop kafka
echo 'Kafka stopped. Payments should commit with PENDING outbox rows; outbox age should rise.'
