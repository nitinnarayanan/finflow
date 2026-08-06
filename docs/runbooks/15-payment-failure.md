# Runbook: Payment failure


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Symptoms

Customer latency/errors, stale or stuck business state, queue/backlog growth, or resource saturation associated with **Payment failure**.

## Detection

Start with transfer success/correctness and p95/p99. Then inspect dependency-specific metrics, saturation, event/outbox age, pod state, and recent release/config changes.

## Metrics

* Request/event rate, errors, duration.
* Relevant pool/queue utilization and saturation.
* Oldest pending state or event age.
* Dependency latency, timeout, reconnect, and circuit state.
* JVM/process health and Kubernetes restarts/throttling when applicable.

## Logs

Query by trace ID, payment ID, normalized error code, dependency, and build/config version. Look for the first causal error rather than repeated downstream symptoms. Confirm logs are not leaking sensitive values.

## Tracing

Compare a failed/slow trace with a healthy baseline. Identify the span consuming the timeout budget, retries, queue wait, database lock wait, and asynchronous handoff.

## Likely root causes

Configuration regression, unbounded concurrency, dependency degradation, hot partition/key/account, schema/contract mismatch, resource leak, lock-order violation, or retry amplification.

## Immediate mitigation

* Stop or reduce the harmful traffic pattern.
* Roll back or disable the recent change when evidence supports it.
* Open a circuit, reduce concurrency, pause unsafe replay, or degrade optional functionality.
* Scale only when the bottleneck can absorb additional load.
* Preserve committed financial state and operator evidence.

## Permanent fix

Protect the invariant with constraints/idempotency, correct timeout and retry ownership, bound pools/queues, improve partition/key strategy, add compatibility tests, automate detection, and test the failure in CI/chaos drills.

## Lessons learned

A restart is mitigation, not root-cause removal. The permanent fix must address the system condition that allowed the incident and add evidence that would detect recurrence earlier.

## Interview talking points

Explain customer impact first, then evidence, mitigation, root cause, prevention, and the trade-off. State your personal ownership accurately and avoid inventing scale or outcomes.
