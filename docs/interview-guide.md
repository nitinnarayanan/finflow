# Interview Mode
For every component explain in this order: requirement → decision → mechanism → failure mode → signal → trade-off → alternative.

## Core talking points
- Financial correctness before raw availability.
- Ledger strong; projections eventual.
- Idempotency key plus request hash plus database uniqueness.
- Business commit plus outbox in one transaction.
- At-least-once consumers deduplicate; never claim global exactly-once.
- Timeout, retry, circuit breaker, and bulkhead are separate controls.
- Fraud fallback is a risk decision, not a generic technical fallback.
- Rollback includes code, database, event schemas, configuration, and in-flight workflows.

## Common follow-ups
Why not a modular monolith? Where is the consistency boundary? What happens after timeout-after-commit? How do you replay safely? What proves the optimization? What becomes the bottleneck at 10x scale?
