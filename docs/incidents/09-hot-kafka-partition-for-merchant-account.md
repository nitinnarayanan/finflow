# Incident 09: Hot Kafka partition for merchant account


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Summary

A production-like failure involving **hot kafka partition for merchant account** affected a defined part of the transfer lifecycle. The incident demonstrates mitigation before perfect diagnosis, evidence preservation, and prevention at the system level.

## Illustrative timeline

| Time | Event |
|---|---|
| 10:00 | Alert fires on customer/business SLI or backlog age. |
| 10:03 | Incident commander establishes scope and freezes risky changes. |
| 10:08 | Metrics isolate the affected dependency/resource. |
| 10:15 | Representative traces and structured logs confirm the failure path. |
| 10:22 | Reversible mitigation reduces customer impact. |
| 10:40 | Business SLI and backlog recovery confirmed. |
| +1 day | Root cause review and permanent actions assigned. |

## Customer impact

Some transfers are delayed, rejected safely, show stale derived information, or require review. The incident description explicitly separates financial correctness from notification/reporting freshness.

## Investigation

1. Compare transfer success/correctness and state age to baseline.
2. Check recent image, configuration, database, and schema changes.
3. Inspect dependency latency/errors and pool/queue saturation.
4. Follow one trace end-to-end and correlate with payment ID.
5. Verify authoritative database state before replaying or compensating.

## Metrics

Transfer state distribution, p95/p99, dependency timeout rate, circuit state, database locks/pool usage, outbox age, partition event age, retries/DLQ, JVM/Kubernetes saturation, and reconciliation mismatch as applicable.

## Example log pattern

```text
level=ERROR service=<service> traceId=<trace> paymentId=<id> errorCode=<normalized-code> dependency=<name> elapsedMs=<value> retryAttempt=<n> message="operation failed safely"
```

## Trace evidence

The critical span shows queue wait, network time, database lock wait, provider latency, or retry amplification. A healthy trace is used as a control.

## Root cause

A missing or incorrect system control—such as uniqueness, lock ordering, timeout budget, bounded concurrency, schema compatibility, probe design, resource sizing, or recovery idempotency—allowed the failure to propagate.

## Immediate mitigation

Reduce the blast radius using rollback, traffic limiting, circuit opening, consumer pause, concurrency reduction, optional-feature degradation, or controlled failover. Do not replay or compensate until the authoritative state is known.

## Permanent fix

Add the missing invariant/control, automated test, dashboard/alert, runbook step, and failure simulation. Review ownership and operational limits across teams.

## Postmortem questions

* Why did the design permit this failure?
* Why was detection later than ideal?
* Which safeguard should have limited impact?
* Was mitigation safe and reversible?
* Which organizational/process factor contributed?

## Lessons learned

Customer impact and correctness define severity. More capacity or retries can amplify a dependency failure. Recovery paths must be tested and idempotent.

## Interview explanation

Use STAR-L: concise situation, your responsibility, concrete evidence-based actions, observable recovery, and the lesson applied later. Never claim an unverified metric or ownership area.
