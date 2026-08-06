# ADR-0001: PostgreSQL ledger is authoritative
Status: Accepted

Decision: financial authorization and postings use PostgreSQL constraints and locks. Redis serves derived views only.

Why: stale cache data must never approve an unsafe transfer.
Alternatives: Redis primary balance, document ledger, fully distributed debit/credit saga.
Trade-offs: stronger correctness and simpler reconciliation; lower write availability and harder horizontal partitioning.
Failure modes: deadlocks, pool saturation, primary outage, replication lag.
Signals: lock waits, deadlocks, p95/p99 query time, pool utilization, reconciliation mismatches.
