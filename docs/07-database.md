# 7. Database Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Current schema

The current repository contains Flyway migrations for `accounts`, `payments`, and `outbox_events`. Account posting locks source and destination rows in sorted UUID order, debits and credits inside one transaction, and relies on JPA versioning/row locks as configured.

## Target schema responsibilities

* `account`: account identity, currency, lifecycle, version, derived balance columns.
* `ledger_entry`: append-only debit/credit postings.
* `account_hold`: reservations affecting available balance.
* `payment`: stable workflow identity and current state.
* `payment_state_history`: immutable transitions.
* `idempotency_record`: principal, key, request hash, response/status.
* `outbox_event`: publication intent committed with business state.
* `reconciliation_exception`: operator-visible mismatches.
* `audit_event`: immutable evidence with strict access/retention.

## Constraints

* Unique idempotency scope and key.
* Positive amount checks.
* Currency and status checks where stable enough for database enforcement.
* Foreign keys within the same service-owned schema.
* Unique external provider reference where semantics require it.
* Ledger debit/credit balancing verified transactionally or by deferred reconciliation controls.

## Index strategy

Indexes are driven by real query plans:

* Payment lookup by id and `(principal_id, idempotency_key)`.
* Stuck-state scan by `(status, updated_at)`.
* Outbox publisher by `(status, created_at)` with bounded batches.
* Account lookup and lock by primary key.
* Ledger history by `(account_id, posted_at desc)`.

Every index increases write cost and memory. Validate with `EXPLAIN (ANALYZE, BUFFERS)` and endpoint metrics.

## Isolation and locking

`READ COMMITTED` is a reasonable default when invariants are protected by row locks and constraints. Pessimistic locking is used for high-contention balance posting; optimistic versioning is preferred for low-contention metadata updates. Serializable isolation is reserved for cases where simpler mechanisms cannot preserve the invariant and its retry cost is acceptable.

## Deadlock prevention

* Lock accounts in a globally consistent order.
* Keep transactions short and avoid remote calls while holding locks.
* Use narrow indexed predicates.
* Retry the entire safe transaction after database deadlock detection with jitter.
* Monitor lock wait time, deadlock count, and blocked sessions.

## Partitioning

Partition large ledger/payment-history tables by time for retention and maintenance, or by account/customer when access patterns justify it. Cross-partition transfers complicate atomicity. Do not shard before measuring the primary bottleneck.

## Replication

Read replicas support reporting and non-critical reads but introduce lag. Consistency-sensitive read-after-write requests go to the primary or use a session-consistency token. Monitor replay lag, replication slots, and failover behavior.

## Migration strategy

Use expand-and-contract:

1. Add backward-compatible columns/tables.
2. Deploy code that understands old and new forms.
3. Backfill in throttled batches.
4. Switch writes and then reads.
5. Observe.
6. Remove old schema in a later release.

Avoid long blocking DDL and make rollback compatible with event and configuration versions.

## Backup and recovery

* Automated full backups plus WAL archiving / point-in-time recovery.
* Encrypted backup storage with tested restore credentials.
* Quarterly restore drills into an isolated environment.
* Define RPO/RTO by data class.
* Reconcile Kafka/outbox state after database recovery.

## Performance tuning checklist

Connection-pool saturation, query p95/p99, buffer/cache hit, rows scanned, lock waits, deadlocks, IO latency, vacuum/bloat, replication lag, and transaction duration. Increase pools only when the database can safely serve the additional concurrency.
