# Configuration Service


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Purpose

Exposes configuration-version diagnostics today; target externalized, reviewed, sanitized configuration distribution.

## Responsibilities

* Own only its bounded-context behavior and data.
* Validate external input and enforce authorization at the resource boundary.
* Propagate trace context and a stable business/correlation identifier.
* Use typed error codes and avoid leaking implementation details or sensitive data.
* Publish metrics for rate, errors, duration, saturation, and business-state correctness.

## APIs and messaging

`GET /api/v1/config/info`.

## Data and cache

**Database:** Git/versioned config metadata; secrets elsewhere.

**Redis:** Client-side config cache with safe refresh.

## Authentication and authorization

The gateway rejects invalid traffic early, but the service remains responsible for resource-level authorization. Internal endpoints require service identity and are not considered safe merely because they are inside the cluster.

## Validation

Bean validation covers structural constraints. Domain validation checks ownership, status, amount, currency, risk policy, and legal state transitions. Validation that protects a financial invariant must be repeated inside the protected transaction where time-of-check/time-of-use races are possible.

## Dependencies and timeout budget

Each remote dependency has an explicit connect/read timeout shorter than the caller's remaining request budget. Retries are bounded, jittered, and owned by one layer. Non-idempotent operations are not blindly retried.

## Deployment and scaling

* Run at least two replicas in production across failure domains when the service is on the critical path.
* Use readiness to withhold traffic during startup and draining.
* Set CPU/memory requests from load evidence, not guesses.
* Scale on request concurrency, queue depth, or consumer lag where more meaningful than CPU.
* Keep pools and queues bounded.

## Monitoring

* HTTP request rate, errors, p50/p95/p99 duration.
* JVM heap, GC pause, CPU, thread count, executor queue depth.
* Database/Kafka/Redis/provider dependency latency and errors as applicable.
* Business metrics specific to the service.
* Configuration and build version labels with cardinality controls.

## Health checks

* **Startup:** local initialization and migration prerequisites.
* **Readiness:** ability to serve meaningful traffic without causing harm.
* **Liveness:** process is making progress; avoid depending on remote services.

## Common failures

Bad config can have fleet-wide blast radius; staged rollout and rollback required.

## Trade-offs and alternatives

A larger modular monolith would reduce remote calls and operational overhead. This service boundary is justified only when ownership, scaling, reliability, security, or release cadence differs materially. A service-per-table design is explicitly rejected.

## Interview questions

1. Why is this a separate service rather than a module?
2. What invariant does it own?
3. What happens when its primary dependency is slow or unavailable?
4. Which metric detects customer impact first?
5. What is the scaling limit after adding more pods?
6. What data may appear in logs and traces?

## Senior-level answer pattern

Lead with the business requirement and consistency boundary. Explain the mechanism, one failure mode, the production signal, the accepted cost, and the alternative you would choose under different requirements.
