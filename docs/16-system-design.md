# 16. System Design Handbook


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Speaking framework

1. Clarify domestic/cross-border, internal/external settlement, TPS, peak factor, currencies, limits, fraud latency, compliance, and response semantics.
2. Identify the hardest invariant: one correct financial effect without overspending.
3. Draw the simplest viable design.
4. Define APIs, data model, ownership, and consistency boundaries.
5. Deep-dive on bottlenecks, failures, security, observability, deployment, and cost.
6. Explain evolution at 10x scale.

## Capacity planning

For each service calculate peak request/event rate, average and p99 service time, desired concurrency, pool sizes, message size, retention, database IOPS, connections, and headroom. Little's Law (`concurrency ≈ throughput × latency`) connects latency to required concurrency. Validate assumptions with load tests and saturation metrics.

## Load balancing

Stateless APIs use layer-7 load balancing with health-aware routing. Avoid sticky sessions by externalizing session state. Use topology-aware routing and connection draining. Load balancing cannot fix a saturated shared database or hot account.

## Sharding

Shard only after vertical optimization, indexing, partitioning, and workload isolation are insufficient. Account/customer-based sharding preserves locality but makes cross-shard transfers difficult. Options include a routing layer, home-shard ownership, escrow/reservation saga, or a dedicated ledger architecture.

## Replication and HA

PostgreSQL uses Multi-AZ synchronous/managed failover for high availability; async replicas serve safe reads. Kafka uses replication factor and min in-sync replicas. Redis uses replicas/sentinel or managed cluster. Application pods span zones with disruption budgets.

## Disaster recovery

Define RTO/RPO separately for ledger, events, cache, audit, and reporting. Backups are useless until restored in a drill. Regional failover considers DNS, database write authority, Kafka offsets, secret availability, external-provider routes, and split-brain prevention.

## Multi-region strategy

Initial target: active/passive for writes, active/read where safe. A single write region simplifies ledger consistency. Active/active money movement requires deterministic ownership, global consensus or carefully designed partitioned ledgers, conflict policy, and much higher operational complexity.

## Consistency

Strong for ledger posting and idempotency; eventual for projections and side effects. The API returns `POSTED`, `PENDING`, `REVIEW_REQUIRED`, or `FAILED` based on actual business state—not whether every downstream consumer completed.

## Bottlenecks

Database lock contention, connection pools, hot accounts, Kafka partition skew, slow fraud/provider calls, cache miss storms, JVM allocation/GC, and network egress. Adding pods helps only when the shared bottleneck and downstream limits can absorb them.

## Future evolution

* Double-entry append-only ledger.
* Debezium/CDC outbox publisher after evaluating operational trade-offs.
* Schema Registry and contract governance.
* Dedicated settlement orchestration/reconciliation service.
* Multi-tenant and multi-currency support.
* Feature-flag service and progressive delivery.
* Workload identity/mTLS and managed secrets.
* Automated chaos scenarios and performance baselines.
* Regional DR exercises and documented capacity models.
