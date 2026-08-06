# Testing Strategy
- Unit: invariants, mappers, validation, policy.
- Slice: MVC/security/JPA behavior.
- Integration: Testcontainers for PostgreSQL, Kafka, and Redis.
- Contract: consumer-driven HTTP and event schema compatibility.
- Concurrency: idempotency race, optimistic conflicts, deterministic lock order.
- Failure: timeout-after-commit, broker outage, poison message, provider slowdown.
- Performance: k6/Gatling workload with saturation and correctness assertions.
Coverage is a diagnostic, not proof of correctness.
