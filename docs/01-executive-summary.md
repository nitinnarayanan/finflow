# 1. Executive Summary


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Business problem

A bank needs to modernize customer-facing account and payment capabilities without replacing every core banking, KYC, settlement, and regulatory platform at once. The legacy estate is tightly coupled, difficult to release independently, slow to diagnose, and vulnerable to partial failures that are hard to reconcile.

FinFlow creates explicit domain boundaries around identity, users, accounts, payment orchestration, financial posting, fraud, notification, reporting, audit, configuration, and legacy integration. The platform prioritizes **correct money movement, traceability, and controlled degradation** over superficial availability.

## Business goals

* Prevent duplicate or unauthorized financial effects.
* Give customers an explicit transfer status instead of ambiguous success.
* Isolate non-critical work such as notifications and reporting from the financial commit path.
* Preserve interoperability with SOAP/mainframe-style systems.
* Reduce time to detect, localize, mitigate, and prevent production incidents.
* Enable independent delivery while maintaining contract and schema compatibility.

## Functional requirements

* Authenticate a customer and issue short-lived access credentials.
* Retrieve account summary and balance with an as-of timestamp.
* Submit a transfer using an idempotency key.
* Validate account ownership, status, currency, limits, and risk policy.
* Post debit and credit safely under concurrency.
* Persist payment state and a durable publication intent.
* Publish payment lifecycle events.
* Notify customers asynchronously.
* Build reporting projections and immutable audit evidence.
* Integrate with external KYC and settlement systems.
* Reconcile transfers stuck in intermediate states.

## Non-functional requirements

| Area | Illustrative target | Design response |
|---|---:|---|
| Correctness | No duplicate financial effect for one idempotency key | Request hash, unique constraint, idempotent state transitions, reconciliation |
| Transfer availability | 99.95% monthly for valid requests | Redundant stateless services, bounded dependency behavior, protected database |
| Transfer latency | p95 < 400 ms; p99 < 900 ms excluding external settlement | Short critical path, bounded timeouts, async side effects |
| Event publication | 99.9% of outbox rows published within 60 seconds | Transactional outbox, backlog monitoring, replayable publisher |
| Notification delay | 99% accepted by provider within 2 minutes | Independent consumer group, retry topics, DLQ |
| Recovery | RTO 30 minutes, RPO near zero for committed ledger data | PostgreSQL backups/WAL, tested restore, durable event recovery |
| Security | Least privilege and auditable sensitive actions | OAuth2/OIDC target, service authorization, encryption, key rotation |

## Expected scale assumptions

The playground uses a design envelope rather than pretending to reproduce hyperscale locally:

* Normal transfer traffic: 500 requests/second.
* Peak factor: 5x during payroll or merchant events.
* Read-to-write ratio: approximately 20:1 for account dashboards.
* Event fan-out: 4–10 consumers per completed transfer.
* Retention: financial/audit records measured in years; operational logs measured in days or months.
* Hot-entity risk: a few merchant or settlement accounts can dominate partition and lock traffic.

These assumptions drive partitioning, connection-pool sizing, queue capacity, and load tests. They must be revised before a real deployment.

## Reliability model

The ledger and committed payment record use strong transactional guarantees within one database boundary. Dashboard projections, notifications, reports, and some external integrations are eventually consistent. Failures are surfaced through explicit states such as `INITIATED`, `REVIEW_REQUIRED`, `POSTED`, `FAILED`, and `SETTLEMENT_PENDING`, rather than hidden behind generic retries.

## Security goals

* Verify issuer, audience, signature, expiry, scopes, and account ownership.
* Use short-lived JWT access tokens and rotated refresh tokens.
* Authenticate service workloads independently of user tokens.
* Avoid secrets in Git, images, logs, traces, and plain ConfigMaps.
* Minimize PII in events and observability data.
* Make privileged and financial actions auditable and tamper-evident.

## Success criteria

A new engineer should be able to trace one transfer from the gateway to database locks, outbox publication, Kafka consumers, dashboards, and incident evidence. An SRE should be able to distinguish customer impact from dependency symptoms. A senior engineer should be able to defend the consistency boundary and explain why every retry, cache, and service boundary exists.
