# 15. Production Incident Handbook


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.

The detailed incident files are intentionally realistic simulations. Timelines and numbers are illustrative.

1. [Duplicate payment after client timeout](incidents/01-duplicate-payment-after-client-timeout.md)
2. [Database deadlock during opposing transfers](incidents/02-database-deadlock-during-opposing-transfers.md)
3. [Redis mass expiration overloads PostgreSQL](incidents/03-redis-mass-expiration-overloads-postgresql.md)
4. [Notification provider causes Kafka consumer lag](incidents/04-notification-provider-causes-kafka-consumer-lag.md)
5. [Poison message creates retry loop](incidents/05-poison-message-creates-retry-loop.md)
6. [Kafka unavailable and outbox backlog grows](incidents/06-kafka-unavailable-and-outbox-backlog-grows.md)
7. [Outbox publisher duplicates an event](incidents/07-outbox-publisher-duplicates-an-event.md)
8. [Schema evolution breaks an older consumer](incidents/08-schema-evolution-breaks-an-older-consumer.md)
9. [Hot Kafka partition for merchant account](incidents/09-hot-kafka-partition-for-merchant-account.md)
10. [Fraud service latency opens circuit breaker](incidents/10-fraud-service-latency-opens-circuit-breaker.md)
11. [Retry storm multiplies downstream traffic](incidents/11-retry-storm-multiplies-downstream-traffic.md)
12. [Thread pool exhaustion in legacy adapter](incidents/12-thread-pool-exhaustion-in-legacy-adapter.md)
13. [PostgreSQL connection-pool saturation](incidents/13-postgresql-connection-pool-saturation.md)
14. [Slow query after missing index](incidents/14-slow-query-after-missing-index.md)
15. [JVM memory leak and OOMKill](incidents/15-jvm-memory-leak-and-oomkill.md)
16. [Long GC pauses cause latency spikes](incidents/16-long-gc-pauses-cause-latency-spikes.md)
17. [CPU throttling from low Kubernetes limit](incidents/17-cpu-throttling-from-low-kubernetes-limit.md)
18. [Bad readiness probe sends traffic too early](incidents/18-bad-readiness-probe-sends-traffic-too-early.md)
19. [Liveness probe creates restart storm](incidents/19-liveness-probe-creates-restart-storm.md)
20. [Expired signing key causes authentication failures](incidents/20-expired-signing-key-causes-authentication-failures.md)
21. [Refresh-token reuse attack detected](incidents/21-refresh-token-reuse-attack-detected.md)
22. [Cache invalidation failure shows stale balance](incidents/22-cache-invalidation-failure-shows-stale-balance.md)
23. [Failed database migration blocks rollout](incidents/23-failed-database-migration-blocks-rollout.md)
24. [Regional dependency outage](incidents/24-regional-dependency-outage.md)
25. [Settlement timeout leaves ambiguous external state](incidents/25-settlement-timeout-leaves-ambiguous-external-state.md)
