# ADR-0006: Asynchronous domain events for side effects

**Status:** Accepted for target architecture; implementation status varies by component.

## Context

FinFlow must preserve financial correctness, provide production diagnosability, and remain operable by a realistic engineering team.

## Problem

Choose a mechanism that satisfies the business invariant without creating unjustified operational complexity or misleading guarantees.

## Decision

Notification/reporting failure must not roll back payment.

## Alternatives considered

Synchronous fan-out, shared database.

## Trade-offs

The decision improves the primary requirement but introduces cost in operations, latency, governance, or migration. Those costs are accepted only within the documented consistency and ownership boundary.

## Consequences

* The choice becomes part of service contracts, dashboards, runbooks, capacity plans, and testing.
* Failure behavior must be explicit and observable.
* Reversal requires compatibility and migration planning rather than a simple code change.
* Interview explanations must state the requirement, alternative, failure mode, and production signal.
