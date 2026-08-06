# 8. Kafka Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Topic design

Business-event topics are preferred over topic-per-consumer. Current code publishes the outbox `eventType` and consumers listen to `payment.completed.v1`.

| Topic | Key | Purpose |
|---|---|---|
| `payment.initiated.v1` | paymentId or accountId | Durable lifecycle start |
| `payment.completed.v1` | accountId when account ordering matters | Notify, report, audit, transaction projection |
| `payment.failed.v1` | paymentId | Support/audit failure projection |
| `*.retry.<delay>.v1` | original key | Delayed retry without blocking live partitions |
| `*.dlq.v1` | original key | Operator-controlled poison-message isolation |

## Partition strategy

Choose a key matching the ordering boundary while spreading load. Account ID preserves per-account ordering but can create hot-account skew. Ordering is not global. Partition count bounds consumer-group parallelism and is difficult to reduce later.

## Producer configuration

Target settings include idempotent producer mode, `acks=all`, appropriate delivery timeout, bounded retries, compression, and strong serialization/schema validation. Producer idempotence protects Kafka retry duplicates; it does not deduplicate a business request.

## Consumer groups

Notification, reporting, audit, and transaction projection each use independent consumer groups. A consumer acknowledges only after its local idempotent effect commits. Rebalances and long blocking provider calls are monitored and isolated.

## Retry and DLQ policy

* Classify transient versus permanent errors.
* Use retry topics with increasing delays rather than sleeping a consumer thread.
* Cap total attempts and elapsed age.
* Preserve original event ID, trace context, schema version, and error metadata.
* DLQ is not a garbage bin; it has ownership, alerting, tooling, and a replay decision process.

## Transactional outbox

Payment state and an outbox row commit atomically. A publisher reads pending rows in bounded batches and publishes to Kafka. Current code polls and waits for send completion before marking `PUBLISHED`. Target improvements include claim/lease semantics, `SKIP LOCKED`, retry scheduling, error reason, metrics, and multiple safe publisher replicas.

## Idempotent consumers

Consumers store processed `eventId` or use a business unique constraint in the same local transaction as their effect. Duplicate delivery is normal. Side effects such as email require provider idempotency/reference or a local attempt state machine.

## Schema evolution

Use an envelope containing `eventId`, `eventType`, `occurredAt`, `producer`, `schemaVersion`, trace/correlation ID, business key, and minimized payload. Enforce backward compatibility through Schema Registry in target state. Add fields with defaults; do not repurpose semantics.

## Replay

Replay requires:

* idempotent and version-aware consumers,
* capacity isolation from live traffic,
* a defined start/end offset and authorization,
* dry-run or shadow mode when possible,
* explicit prohibition on blindly replaying external payment side effects.

## Monitoring and capacity

Event age is usually more actionable than lag count alone. Track partition lag, oldest event age, throughput, processing duration, error/retry/DLQ rates, rebalance frequency, broker disk, under-replicated partitions, and outbox age. Capacity-plan peak event rate, replication factor, retention, message size, partition skew, and consumer processing time.

## Common issues

* Slow provider blocks consumer threads and grows lag.
* Poison message creates retry loop.
* Hot partition limits scale despite idle consumers.
* Incompatible schema breaks older consumers.
* Rebalance storm pauses processing.
* Kafka outage grows outbox backlog while payments continue within policy/capacity.
