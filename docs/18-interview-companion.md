# 18. Interview Companion


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Universal senior answer

> “The requirement was ____. I chose ____ because ____. The mechanism was ____. The main failure mode was ____, so we monitored ____ and mitigated with ____. The trade-off was ____. I would choose ____ instead when ____.”

## Core decision prompts

### Microservices

**Best answer:** boundaries follow business ownership, scaling, reliability, and release cadence. Strongly transactional operations remain in the smallest practical boundary.

**Common mistake:** “Microservices scale better.”

**Follow-up:** When would you use a modular monolith?

### Idempotency

**Best answer:** same logical request produces one financial effect and a consistent response; key is bound to principal/operation and request hash, enforced by database uniqueness.

**Common mistake:** application-only `exists()` check that races across replicas.

**Whiteboard:** client timeout after commit, retry with same key, lookup stored result.

### Outbox

**Best answer:** business row and publication intent commit atomically; publisher can retry after crashes. It closes the dual-write gap but introduces backlog, cleanup, ordering, and duplicate-delivery concerns.

### Kafka exactly once

**Best answer:** Kafka can provide scoped transactional semantics inside Kafka, but databases and providers remain outside. End-to-end correctness uses idempotency and reconciliation.

### Locking

**Best answer:** optimistic versioning for low contention; pessimistic row locks for hot money movement. Lock in consistent order, keep transactions short, monitor waits/deadlocks.

### Redis

**Best answer:** cache-aside for derived views with TTL jitter, coalescing, and as-of timestamps; never authorize from stale cache. Protect PostgreSQL during cache failure.

### Resilience

**Best answer:** timeout first, then safe bounded retry, circuit breaker, bulkhead, and business fallback. Avoid retry ownership at multiple layers.

### Observability

**Best answer:** customer/business SLI first; metrics show pattern, traces localize the path, logs explain details. Correlation ID and payment ID have different lifetimes.

### Security

**Best answer:** gateway validates early, service authorizes resource ownership, workload identity authenticates internal calls, tokens are short-lived, secrets/keys rotate, PII is minimized and audit is separate.

## Behavioral signals

* Ownership: finds and closes risk outside a narrow ticket.
* Judgment: chooses simpler architecture when distribution is not justified.
* Incident leadership: establishes impact, parallelizes investigation, records decisions, and communicates clearly.
* Credibility: separates personally implemented behavior from platform/team ownership and recommendations.
* Learning: converts incidents into tests, dashboards, standards, and runbooks.
