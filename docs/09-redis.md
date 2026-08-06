# 9. Redis Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


## Role

Redis is a disposable, low-latency projection store for account summaries, recent activity, selected reporting summaries, rate-limit state, and short-lived reference data. It never authorizes a transfer or replaces durable ledger state.

## Cache-aside flow

1. Read `account-summary:v2:{accountId}`.
2. On hit, return value with version and `asOf` timestamp.
3. On miss, read PostgreSQL.
4. Coalesce concurrent misses for hot keys.
5. Populate with bounded TTL plus random jitter.
6. After committed writes, invalidate or refresh the projection.

## TTL and invalidation

TTL controls staleness and traffic shape. Identical expiry times can cause a mass miss and database overload. Use jitter, refresh-ahead for hot keys, and event-driven invalidation. A failed invalidation produces stale data, so responses expose `asOf` and projections are never used for financial authorization.

## Stampede prevention

* Per-key request coalescing or short distributed lock.
* Refresh-ahead before expiry.
* TTL jitter.
* Bounded database fallback concurrency.
* Load shedding and stale-while-revalidate for explicitly safe views.

## Hot keys

Measure per-key or sampled access concentration. A single merchant/settlement account can bottleneck a shard. Techniques include local near-cache for immutable data, replicated derived values, key redesign, or splitting a projection—not splitting the authoritative account invariant casually.

## Eviction and sizing

Choose an eviction policy from the workload. Reserve memory headroom for fragmentation and failover. Estimate serialized object size × key count × replication/overhead, then validate with production-like data. Monitor used memory, fragmentation ratio, evictions, hit rate, command latency, and reconnects.

## Persistence and HA

Cache durability is not ledger durability. Redis persistence may reduce warm-up cost but does not change the source of truth. Understand replica promotion, client reconnection, stale replicas, and data-loss windows.

## Failure behavior

When Redis is unavailable, fail open to PostgreSQL only if bounded capacity remains. Otherwise degrade optional dashboard features, apply request limits, and protect the database. Alert on hit-rate collapse and DB fallback load before customers see broad latency.
