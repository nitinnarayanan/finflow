# 20. Learning Roadmap


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Beginner

**Study:** HTTP, REST, Spring Boot controllers, dependency injection, validation, JPA basics, Docker Compose.

**Experiments:** Run login, account read, and transfer; inspect PostgreSQL rows; stop one service; follow logs.

**Misconception:** annotations replace understanding of transactions and proxies.

## Intermediate

**Study:** database transactions, Flyway, Kafka producer/consumer, Redis cache-aside, JWT validation, Actuator/Micrometer.

**Experiments:** implement Redis account-summary cache; add TTL jitter; write Testcontainers tests; trigger duplicate requests.

**Interview appearance:** explain request flow and basic trade-offs.

## Advanced

**Study:** idempotency, outbox, lock contention, retry/DLQ, schema evolution, tracing, performance testing.

**Experiments:** add request hash conflict; safe multi-replica outbox claims; poison-message DLQ; timeout-after-commit chaos test; deadlock test.

**Misconception:** Kafka or retries provide end-to-end exactly once.

## Senior

**Study:** SLOs/error budgets, incident command, capacity planning, progressive delivery, threat modeling, reconciliation.

**Experiments:** build dashboards and burn-rate alerts; conduct cache/Kafka/provider outage game days; write postmortems; add double-entry ledger.

**Interview appearance:** defend alternatives, failure modes, metrics, and ownership.

## Staff

**Study:** cross-team contracts, platform standards, migration strategy, multi-region trade-offs, cost and organizational topology.

**Experiments:** introduce Schema Registry governance; design active/passive DR; create shared resilience policy; review service boundaries and merge unjustified services.

**Behavioral signal:** improves the system and the way teams make decisions.

## Principal

**Study:** enterprise risk, regulatory/control design, long-horizon architecture, socio-technical failure, portfolio-level reliability and cost.

**Experiments:** define a multi-year ledger evolution, regional write strategy, control evidence model, capacity economics, and deprecation program. Run principal-level review: invariant, blast radius, reversibility, operability, ownership, and future constraints.

## Component experiment matrix

| Component | Modification | Failure simulation | Learning outcome |
|---|---|---|---|
| Payment | Add persisted idempotency response | Timeout after DB commit | Ambiguous failure and replay safety |
| Account | Add double-entry ledger/holds | Opposing concurrent transfers | Locking and invariants |
| Kafka | Add retry/DLQ + schema registry | Poison and incompatible events | At-least-once operations |
| Redis | Add coalescing/refresh-ahead | Mass expiry/outage | Cache as traffic shaping |
| Fraud | Add policy-based degradation | Slow/error mode | Technical versus business fallback |
| Kubernetes | Add topology spread/canary | Kill pods/zone simulation | Availability and rollout safety |
| Observability | Add exemplars/SLO alerts | Latency and lag injection | Evidence-led troubleshooting |
| Security | Replace demo auth with OIDC | Key rotation/token reuse | Real token lifecycle |
