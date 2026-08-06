# 11. Observability Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Three pillars and business state

Metrics identify patterns, traces localize a path, and logs explain details. Business-state evidence—payment state, age, idempotency result, reconciliation status—is equally important because infrastructure can appear healthy while money movement is incorrect.

## Structured logging

Every log uses JSON in target state and includes timestamp, service, environment, severity, trace ID, span ID, correlation/business ID, normalized error code, event/action name, build version, and safe dimensions. Tokens, account numbers, payload PII, and secrets are masked. Audit, business, operational, and debug logs have different retention and access.

## Tracing

OpenTelemetry propagates context across gateway, HTTP clients, database calls, and Kafka headers. One payment may span multiple traces across its lifecycle; therefore `paymentId` remains searchable separately from `traceId`. Sampling retains errors and slow traces more aggressively while controlling cost and cardinality.

## Metrics

### RED for services

* Rate: requests/events processed.
* Errors: technical and business failures separated.
* Duration: p50/p95/p99 by endpoint/dependency.

### USE for resources

* Utilization: CPU, heap, pool usage.
* Saturation: queues, thread pools, DB connections, Kafka lag.
* Errors: OOM kills, timeouts, failed connections, throttling.

### Business metrics

Transfer accepted/posted/failed/review counts, duplicate suppression, idempotency conflict, stuck-state age, outbox age, reconciliation mismatch, degraded-fraud volume, notification delay, and stale-cache age.

## SLI/SLO examples

| Capability | SLI | Illustrative SLO |
|---|---|---|
| Transfer correctness | Correctly processed valid transfers / valid attempts | 99.95%; correctness errors tracked separately |
| API latency | End-to-end duration | p95 < 400 ms, p99 < 900 ms excluding settlement |
| Outbox publication | Age of oldest unpublished row | 99.9% under 60 seconds |
| Notification | Event-to-provider acceptance | 99% under 2 minutes |
| Dashboard | Successful summary reads with freshness | 99.9%, stale age visible |

## Error budgets

Availability error budgets do not excuse correctness defects. A duplicate debit is a severity-one correctness event even if aggregate API availability remains within SLO. Burn-rate alerts use fast and slow windows to detect acute and chronic consumption.

## Dashboards

1. Customer journey: transfer success, latency, state distribution.
2. Dependency map: fraud/account/DB/Kafka/Redis/provider errors and latency.
3. Saturation: JVM, pools, queues, DB connections, broker lag.
4. Async health: outbox age, consumer event age, retry/DLQ depth.
5. Release comparison: version, canary versus baseline, error budget burn.
6. Incident drill: trace exemplars linked from latency/error graphs.

## Alert principles

Alert on customer impact, backlog age, and exhaustion risk—not every transient error. Every paging alert links to an owner, dashboard, runbook, and safe mitigation. Avoid high-cardinality labels such as raw customer or payment IDs in metrics.
