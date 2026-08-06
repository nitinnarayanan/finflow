# Interview Handbook Alignment


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


This suite is grounded in the uploaded *Senior Java / Backend Interview Handbook*: financial correctness before raw availability; authoritative ledger with Redis projections; idempotency and database uniqueness; transactional outbox; explicit transfer states; risk-aware fraud degradation; Kubernetes-native discovery; metrics/logs/traces; safe rollout; and production incident reasoning.

The documentation expands those principles into an engineering operating model. It does not claim that every target-state control is already implemented in the compact V1 codebase. Each service document identifies current and target behavior.

## Credibility rules

* Do not present illustrative SLOs, traffic, latency, or outcomes as employer facts.
* Separate personal implementation, team architecture, and recommended future design.
* Do not claim exactly-once, zero downtime, or strong consistency without naming the exact boundary and mechanism.
* For each design, prepare one failure mode, one production signal, one trade-off, and one alternative.
