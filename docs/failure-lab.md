# Failure Lab

Each lab follows: reproduce → symptoms → evidence → mitigation → root cause → prevention.

| Scenario | Trigger | Expected signal | Primary mitigation |
|---|---|---|---|
| Kafka down | `scripts/failures/kafka-down.sh` | outbox age/backlog rises; payment commit remains available | restore broker; throttle replay |
| PostgreSQL down | stop postgres | readiness fails, Hikari errors, payment failures | fail closed; restore primary |
| Duplicate request | duplicate script | one logical effect due to unique idempotency key | inspect request hash and row |
| Deadlock | concurrent inverse transfers | deadlock exceptions and lock waits | bounded whole-transaction retry with jitter |
| Slow provider | legacy `delayMs` | latency, pool usage, circuit state | timeout, bulkhead, circuit breaker |
| Poison message | publish malformed payload | retry/DLQ depth | quarantine and fix schema/consumer |
| Cache stampede | expire hot keys together | hit-rate collapse, DB spike | coalesce, jitter TTL, warm keys |
| Retry storm | enable retries at multiple layers | downstream call amplification | designate one retry owner |
| Memory leak | failure profile retaining buffers | heap growth and GC pressure | capture heap dump; remove retention |
| Regional outage | stop regional dependency proxy | region errors/backlog | execute tested traffic-shift policy |
