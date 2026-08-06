# 17. Developer Guide


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Repository structure

```text
api-gateway/                 edge routing and security scaffold
auth-service/                demo authentication; OIDC target
user-service/                customer-domain scaffold
account-service/             authoritative account read/posting
payment-service/             idempotency, orchestration, outbox
fraud-service/               risk simulation and failure injection
notification-service/        Kafka consumer scaffold
transaction-service/         transaction projection scaffold
reporting-service/           reporting consumer scaffold
audit-service/               audit consumer scaffold
legacy-integration-service/  SOAP/mainframe simulation
configuration-service/       config diagnostics scaffold
platform-common/             shared technical primitives
infra/                       Prometheus/Grafana/PostgreSQL assets
k8s/                         Kubernetes manifests
scripts/                     startup, demo, and failure scripts
docs/                        engineering documentation
```

## Local run

1. Install Java 21, Maven, Docker, and Docker Compose.
2. Run `docker compose up -d` or `scripts/start-infra.sh`.
3. Build with `mvn -T 1C clean verify`.
4. Start services from the IDE or with Maven profiles/scripts.
5. Import `postman/FinFlow.postman_collection.json`.
6. Use the seeded account IDs from the Flyway migration.

The generated repository was not build-verified in the original generation environment because Maven was unavailable; treat the first local build as a baseline validation step.

## Debugging

* Set breakpoints at gateway filter/security, payment idempotency, fraud client, account lock/posting, payment transaction commit, outbox publisher, and Kafka consumer.
* Query payments/outbox rows before and after failure injection.
* Search logs by trace/payment ID.
* Open Zipkin for the distributed path and Grafana for system patterns.
* Capture thread/heap dumps before restart when safe.

## Add a new API

1. Define business requirement and owning bounded context.
2. Write API contract and stable error semantics.
3. Add DTO validation; do not expose JPA entities.
4. Enforce authorization and resource ownership.
5. Implement domain logic and invariant tests.
6. Add integration/contract tests and observability.
7. Update OpenAPI/Postman, docs, dashboard, runbook, and ADR if architectural.

## Add a Kafka event

1. Define semantic event and owner; avoid topic-per-consumer.
2. Add versioned envelope/schema and compatibility test.
3. Choose partition key from ordering boundary.
4. Publish through outbox for DB-coupled state changes.
5. Make every consumer idempotent.
6. Define retries, DLQ, replay authorization, metrics, and retention.

## Add a service

A new service requires a distinct business capability, data owner, release/scaling/reliability need, and operational owner. Do not create a service for a utility or table. Provide SLO, dashboards, alerts, runbook, security classification, deployment, capacity model, API/event contracts, and migration plan.

## Coding conventions

* Constructor injection and immutable dependencies.
* Java records for external DTOs where appropriate; domain behavior remains explicit.
* No floating-point money.
* No remote calls inside database lock/transaction scope unless unavoidable and documented.
* Normalized exceptions and error codes.
* Structured parameterized logging with no sensitive payloads.
* Bounded executors, queues, retries, and caches.
* Package by domain/feature in mature modules; keep adapters separated from domain.

## Testing strategy

* Unit: domain invariants and state transitions.
* Slice: validation, serialization, security, mapping.
* Testcontainers: PostgreSQL locks/constraints, Kafka delivery, Redis behavior.
* Contract: HTTP and event compatibility.
* End-to-end: only critical journeys.
* Concurrency: duplicate requests, overspend, deadlocks.
* Performance: p95/p99 and saturation at expected peak.
* Chaos: timeout-after-commit, broker/cache/provider failure.
