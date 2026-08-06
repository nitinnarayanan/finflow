# 19. Senior Engineering Cheat Sheet


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Architecture

* Lead with requirement and invariant, not tools.
* Service boundary requires ownership/scaling/reliability/release justification.
* Ledger strong; projections eventual.
* Keep strongly transactional work together.

## Distributed systems

* Networks fail and time out ambiguously.
* Expect duplicate delivery.
* Ordering is scoped.
* Retries amplify.
* Clocks skew and caches become stale.
* Persist workflow state and reconcile incomplete work.

## Reliability

* Timeout every remote call.
* One retry owner, bounded attempts, exponential backoff, jitter.
* Circuit breaker protects resources; business policy defines fallback.
* Bulkhead by dependency.
* Graceful shutdown, connection draining, correct probes.

## Database

* Protect invariants with constraints.
* Choose isolation consciously.
* Keep transactions short and lock consistently.
* Index from query plans.
* Monitor locks, pool saturation, lag, and transaction duration.
* Expand-and-contract migrations.

## Kafka

* Deliberate partition key.
* Version schemas.
* Outbox publication.
* Idempotent consumers.
* Retry topics and owned DLQ.
* Monitor event age.
* Control replay.
* Never overclaim exactly once.

## Redis

* Derived views only.
* TTL jitter, request coalescing, refresh-ahead.
* Expose freshness.
* Protect DB fallback.
* Watch hot keys, evictions, fragmentation, reconnects.

## Observability

* Metrics identify patterns.
* Traces localize paths.
* Logs explain details.
* Business state proves correctness.
* RED for services; USE for resources.
* Page on impact and backlog age.

## Incident response

Impact → recent change → metrics → traces → logs → mitigation → confirm recovery → RCA → prevention.

## Leadership language

* “I separated the correctness boundary from the availability boundary.”
* “I assigned one retry owner to avoid multiplicative traffic.”
* “We mitigated first while preserving evidence.”
* “The circuit breaker was not the business fallback.”
* “I would choose a modular monolith unless distribution earns its cost.”
* “That number is illustrative; I would use the verified SLO in a real interview.”
