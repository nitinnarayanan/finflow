# 6. Distributed Systems Design


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Why microservices

Chosen for clear domain ownership, release independence, differentiated reliability, and scaling. Payments need strict correctness and controlled change; notifications are bursty and can fail without blocking the financial commit. Rejected assumption: microservices are automatically more scalable. They add network latency, partial failure, contract evolution, distributed tracing, and operational cost.

**Rejected alternative:** a single monolith. A **modular monolith** remains a credible alternative for an earlier stage or smaller team and should be preferred when independent ownership and scaling do not justify distribution.

## Why Spring Boot and Java 21

Spring Boot offers mature transaction, security, validation, Kafka, data, Actuator, and observability integration. Java 21 provides a current LTS runtime and modern language/runtime capabilities. The trade-off is framework complexity, startup/memory overhead, and the need to understand proxy and transaction semantics rather than relying on annotations mechanically.

## Why PostgreSQL

Money movement needs ACID transactions, constraints, precise locks, mature indexing, and operational tooling. PostgreSQL is authoritative for payments, accounts, outbox, and ledger-related state. The scaling cost is connection management and eventual partition/sharding complexity.

## Why Kafka

Kafka provides durable, replayable event streams and independent consumer groups. It is suitable for asynchronous side effects and integrations, not immediate request/response decisions. Ordering is only within a partition. Kafka transactions do not make external databases or providers exactly once.

**RabbitMQ alternative:** simpler queue-oriented routing and per-message acknowledgement can be attractive for task distribution. Kafka was chosen because replay, retention, fan-out, and stream history are central. RabbitMQ remains reasonable for transient command queues.

## Why Redis

Redis supports low-latency derived account summaries, recent activity, and reference data. It is not the ledger. Failure must not automatically send unbounded load to PostgreSQL. TTL jitter, request coalescing, refresh-ahead, and load shedding protect the database.

## Why REST

REST/HTTP is used for synchronous, externally understandable operations where immediate responses matter. It benefits from tooling and explicit resource semantics. gRPC is a viable internal alternative when strongly typed low-latency calls and streaming dominate, but browser exposure and operational familiarity favor HTTP here.

## Why asynchronous events

Events remove slow, non-critical work from the transfer response and let consumers scale/fail independently. The cost is eventual consistency, duplicate delivery, schema governance, replay safety, and additional operational state.

## Why Kubernetes

Kubernetes provides scheduling, service DNS, rollout strategies, readiness, liveness, autoscaling, disruption controls, and workload identity integration. It is not free scalability: bad probes, resource limits, or retry policies can worsen an outage. A simpler VM/container platform is a valid alternative for smaller operational teams.

## Consistency model

* Ledger/posting: strong consistency within database transaction boundary.
* Payment workflow: explicit state machine with idempotent transitions.
* Events: at-least-once delivery with deduplicating consumers.
* Redis/reporting/notifications: eventual consistency with freshness/age metrics.
* External settlement: saga/reconciliation because no shared transaction exists.

## Resilience order of reasoning

1. Set a timeout.
2. Decide whether the operation is safely retryable.
3. Assign one retry owner with bounded attempts and jitter.
4. Add a circuit breaker to stop repeated known-bad calls.
5. Add a bulkhead to prevent shared resource exhaustion.
6. Define the business fallback; the circuit breaker itself is not the fallback.
