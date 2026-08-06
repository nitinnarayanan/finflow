# 3. Domain Model


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Bounded contexts

| Context | Owns | Does not own |
|---|---|---|
| Identity | Credentials, token sessions, refresh rotation | Account ownership rules |
| Customer/User | Customer profile and preferences | Ledger balance |
| Accounts/Ledger | Account lifecycle, available/posted balance, holds, postings | Notification delivery |
| Payments | Transfer intent, idempotency, workflow state, risk result | Provider implementation details |
| Fraud | Risk decision, reason codes, rule/model version | Financial posting |
| Notifications | Preferences, templates, provider attempts | Payment correctness |
| Reporting | Materialized analytical views | Authoritative transaction state |
| Audit | Immutable business/security evidence | Operational debug logs |
| Legacy Integration | Translation to external contracts | Domain model ownership |

## Aggregate model

```mermaid
classDiagram
  class Customer {
    UUID id
    String status
    String riskTier
  }
  class Account {
    UUID id
    UUID customerId
    String currency
    Decimal postedBalance
    Decimal availableBalance
    String status
    long version
  }
  class Payment {
    UUID id
    String idempotencyKey
    String requestHash
    UUID sourceAccountId
    UUID destinationAccountId
    Decimal amount
    String currency
    PaymentStatus status
    String riskDecision
    long version
  }
  class LedgerEntry {
    UUID id
    UUID accountId
    UUID paymentId
    EntryType type
    Decimal amount
    Instant postedAt
  }
  class OutboxEvent {
    UUID id
    UUID aggregateId
    String eventType
    int schemaVersion
    String payload
    String status
  }
  class AuditEvent {
    UUID id
    String actor
    String action
    String resource
    String decision
    String correlationId
  }
  Customer "1" --> "many" Account
  Payment --> Account : source
  Payment --> Account : destination
  Payment "1" --> "many" LedgerEntry
  Payment "1" --> "many" OutboxEvent
  Payment "1" --> "many" AuditEvent
```

## ER model: target state

```mermaid
erDiagram
  CUSTOMER ||--o{ ACCOUNT : owns
  ACCOUNT ||--o{ ACCOUNT_HOLD : has
  PAYMENT ||--o{ PAYMENT_STATE_HISTORY : records
  PAYMENT ||--o{ LEDGER_ENTRY : causes
  PAYMENT ||--o{ OUTBOX_EVENT : emits
  PAYMENT ||--o{ IDEMPOTENCY_RECORD : protected_by
  PAYMENT ||--o{ RECONCILIATION_EXCEPTION : may_create
  ACCOUNT ||--o{ LEDGER_ENTRY : receives

  PAYMENT {
    uuid id PK
    string idempotency_key UK
    string request_hash
    uuid source_account_id
    uuid destination_account_id
    decimal amount
    string currency
    string status
    string risk_decision
    bigint version
    timestamp created_at
    timestamp updated_at
  }
```

## Core business rules

* Amount must be positive and represented using decimal arithmetic.
* Source and destination must be different and currency-compatible unless FX is explicit.
* Source account must be active, owned/authorized, and have sufficient available funds.
* An idempotency key is scoped to an authenticated principal and operation.
* Reusing a key with a different request hash is a conflict, not a replay.
* Debit and credit must either commit together within the ledger boundary or be represented by a recoverable saga with explicit states.
* Redis balance is never used for authorization.
* Every state transition is append-auditable; mutable current state is not the complete history.
* Fraud outage behavior is policy-dependent: high-risk fails closed/review; constrained low-risk degradation is explicit and measured.

## Domain events

| Event | Producer | Key | Consumers | Compatibility rule |
|---|---|---|---|---|
| `payment.initiated.v1` | Payment | paymentId/accountId | Fraud analytics, audit | Additive fields only |
| `payment.completed.v1` | Payment | accountId or paymentId | Notifications, reporting, transaction, audit | Stable envelope and semantic meaning |
| `payment.failed.v1` | Payment | paymentId | Audit, support projection | Reason codes versioned |
| `fraud.alert.raised.v1` | Fraud | customerId | Case management, audit | PII minimized |
| `customer.notification.requested.v1` | Payment/Notification orchestrator | customerId | Notification workers | Channel preferences resolved carefully |

## Aggregate boundaries and concurrency

The account/ledger aggregate protects funds. The payment aggregate protects workflow identity and state. The current playground uses deterministic pessimistic account-row locking for posting. A fuller production design would store double-entry ledger rows and compute or materialize balances from postings and holds.

## Invariants versus projections

| Invariant | Source of truth | Projection allowed? |
|---|---|---|
| No overspend | Ledger transaction and locks | No |
| One effect per idempotency key | DB unique constraint and request hash | No |
| Dashboard balance | Account/ledger | Yes, with `asOf` and version |
| Notification status | Notification store/provider response | Yes |
| Reporting totals | Event-derived analytical view | Yes, reconciled |
