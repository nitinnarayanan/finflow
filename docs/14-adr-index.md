# 14. Architecture Decision Records


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.

* [ADR-0001: PostgreSQL as authoritative financial store](adr/0001-postgresql-as-authoritative-financial-store.md)
* [ADR-0002: Kafka instead of RabbitMQ for integration events](adr/0002-kafka-instead-of-rabbitmq-for-integration-events.md)
* [ADR-0003: Redis only for derived cache projections](adr/0003-redis-only-for-derived-cache-projections.md)
* [ADR-0004: Spring Boot and Java 21](adr/0004-spring-boot-and-java-21.md)
* [ADR-0005: REST for synchronous APIs](adr/0005-rest-for-synchronous-apis.md)
* [ADR-0006: Asynchronous domain events for side effects](adr/0006-asynchronous-domain-events-for-side-effects.md)
* [ADR-0007: Saga orchestration for external settlement](adr/0007-saga-orchestration-for-external-settlement.md)
* [ADR-0008: Transactional outbox](adr/0008-transactional-outbox.md)
* [ADR-0009: Kubernetes-native discovery and deployment](adr/0009-kubernetes-native-discovery-and-deployment.md)
* [ADR-0010: Pessimistic locking for hot balance posting](adr/0010-pessimistic-locking-for-hot-balance-posting.md)
* [ADR-0011: Cache-aside with TTL jitter](adr/0011-cache-aside-with-ttl-jitter.md)
* [ADR-0012: Short-lived JWT access tokens](adr/0012-short-lived-jwt-access-tokens.md)
