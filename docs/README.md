# FinFlow Enterprise Engineering Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.


FinFlow is a production-learning platform for digital banking and domestic money transfer. The documentation is written for onboarding engineers, design reviewers, SREs, architects, and senior-level interview preparation.

## Reading order

1. [Executive Summary](01-executive-summary.md)
2. [High-Level Architecture](02-high-level-architecture.md)
3. [Domain Model](03-domain-model.md)
4. [Microservice Catalog](04-microservices.md)
5. [End-to-End Flows](05-end-to-end-flows.md)
6. [Distributed Systems Design](06-distributed-systems.md)
7. [Database Engineering](07-database.md)
8. [Kafka Engineering](08-kafka.md)
9. [Redis Engineering](09-redis.md)
10. [Security](10-security.md)
11. [Observability](11-observability.md)
12. [Production Troubleshooting](12-troubleshooting.md)
13. [Deployment and Release Engineering](13-deployment.md)
14. [Architecture Decision Records](14-adr-index.md)
15. [Production Incident Handbook](15-incident-handbook.md)
16. [System Design Handbook](16-system-design.md)
17. [Developer Guide](17-developer-guide.md)
18. [Interview Companion](18-interview-companion.md)
19. [Senior Engineering Cheat Sheet](19-cheat-sheet.md)
20. [Learning Roadmap](20-learning-roadmap.md)

## Core design invariant

**Financial correctness is stronger than projection freshness.** PostgreSQL-backed ledger/account state is authoritative. Redis is a disposable read optimization. Kafka is a durable integration mechanism, not the financial commit boundary. A payment can be committed while downstream notifications or reporting remain delayed.

## Repository reality

The current code implements a compact core: gateway routing, demo authentication, account reads and deterministic row-lock posting, payment idempotency, payment/outbox persistence, a polling outbox publisher, Kafka consumers, failure injection in fraud and legacy adapters, Docker Compose infrastructure, basic Kubernetes manifests, and observability scaffolding. Several enterprise controls described in these documents are target-state or labs and are labeled accordingly.
