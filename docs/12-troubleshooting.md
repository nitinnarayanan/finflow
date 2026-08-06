# 12. Production Troubleshooting Guide


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.

## Incident operating model

1. Establish customer and financial impact.
2. Check recent deploy/config/schema changes.
3. Use metrics to narrow scope before searching logs.
4. Follow traces for representative failures.
5. Mitigate using the lowest-risk reversible action.
6. Preserve evidence before restart when safe.
7. Confirm recovery with business SLIs, not only process health.
8. Complete a blameless postmortem and prevention work.

## Playbooks

1. [Kafka consumer lag](runbooks/01-kafka-consumer-lag.md)
2. [Redis outage or hit-rate collapse](runbooks/02-redis-outage-or-hit-rate-collapse.md)
3. [PostgreSQL unavailable](runbooks/03-postgresql-unavailable.md)
4. [Database deadlock](runbooks/04-database-deadlock.md)
5. [Slow query](runbooks/05-slow-query.md)
6. [Memory leak](runbooks/06-memory-leak.md)
7. [High CPU](runbooks/07-high-cpu.md)
8. [GC pause](runbooks/08-gc-pause.md)
9. [Thread-pool exhaustion](runbooks/09-thread-pool-exhaustion.md)
10. [Network latency](runbooks/10-network-latency.md)
11. [Third-party provider degradation](runbooks/11-third-party-provider-degradation.md)
12. [Pod crash / OOMKill](runbooks/12-pod-crash-oomkill.md)
13. [Deployment failure](runbooks/13-deployment-failure.md)
14. [Authentication failure](runbooks/14-authentication-failure.md)
15. [Payment failure](runbooks/15-payment-failure.md)
16. [Data inconsistency](runbooks/16-data-inconsistency.md)
17. [Cache inconsistency](runbooks/17-cache-inconsistency.md)
18. [Outbox backlog](runbooks/18-outbox-backlog.md)
19. [Retry storm](runbooks/19-retry-storm.md)
20. [Poison message](runbooks/20-poison-message.md)
