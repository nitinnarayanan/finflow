# Architecture

```mermaid
flowchart LR
 C[Web/Mobile Client] --> G[API Gateway]
 G --> A[Auth Service]
 G --> P[Payment Service / Saga Orchestrator]
 P --> F[Fraud Service]
 P --> AC[Account Service / Authoritative Ledger]
 P --> O[(Payment DB + Outbox)]
 O --> OP[Outbox Publisher] --> K[(Kafka)]
 K --> N[Notification]
 K --> T[Transaction Projection]
 K --> R[Reporting]
 K --> AU[Audit]
 AC --> PG[(PostgreSQL)]
 T --> RD[(Redis projection)]
 P --> L[Legacy Adapter / SOAP simulation]
```

## Correctness boundary
The authoritative debit/credit operation remains inside the account ledger transaction. Payment orchestration holds workflow state and uses idempotency plus an outbox. Redis is only a derived view. Kafka is at-least-once; consumers must deduplicate.

## Service discovery
Local execution uses explicit URLs. Kubernetes uses stable Service names and cluster DNS; Eureka is intentionally omitted to avoid overlapping discovery systems.
