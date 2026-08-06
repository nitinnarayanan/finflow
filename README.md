# FinFlow — Production-Style Digital Banking Playground

FinFlow is a Java 21 / Spring Boot multi-service learning system for payment correctness, distributed workflows, failure injection, observability, and senior-level system-design interviews. It is deliberately not a CRUD tutorial.

## Implemented core path
- Payment API with idempotency key, request hash, DB uniqueness, explicit states, and transactional outbox.
- Account service with an authoritative PostgreSQL balance and deterministic pessimistic locking for atomic debit/credit.
- Synchronous fraud decision and asynchronous Kafka side effects.
- Independent consumer groups for transaction projection, notification, reporting, and audit.
- Gateway, demo auth, user/configuration, fraud, legacy adapter, metrics, traces, Docker infrastructure, Kubernetes manifests, ADRs, runbooks, and failure scripts.

## Deliberate simulations / extension points
The demo auth issuer, notification provider, schema registry, multi-region control plane, secret manager, immutable audit storage, Redis read model, retry topics/DLQ automation, and deployment controller are represented with clear contracts and documentation but should be upgraded in later labs. This distinction prevents claiming a simulated control as production-complete.

## Quick start
Prerequisites: Java 21, Maven 3.9+, Docker, Docker Compose, curl, jq.

```bash
./scripts/start-infra.sh
mvn -T 1C clean package -DskipTests
# Start services from your IDE, or in separate terminals:
mvn -pl account-service spring-boot:run
mvn -pl fraud-service spring-boot:run
mvn -pl payment-service spring-boot:run
mvn -pl notification-service spring-boot:run
./scripts/demo-transfer.sh
```

Seed accounts:
- Source: `11111111-1111-1111-1111-111111111111` ($5,000)
- Destination: `22222222-2222-2222-2222-222222222222` ($1,000)

Dashboards: Grafana `http://localhost:3000`, Prometheus `http://localhost:9090`, Zipkin `http://localhost:9411`.

## Recommended learning order
1. Read `docs/architecture.md` and ADRs.
2. Trace one transfer from request to outbox to consumers.
3. Repeat the same idempotency key concurrently.
4. Stop Kafka and inspect pending outbox rows.
5. Trigger slow legacy calls and inspect thread/pool behavior.
6. Add retry topics and DLQ, then safely replay a poison message.
7. Replace demo auth with Keycloak and enforce resource ownership.

## Repository map
- `payment-service`: workflow orchestration, idempotency, outbox.
- `account-service`: authoritative money posting and concurrency control.
- `fraud-service`: risk decision and degradation lab.
- `transaction/notification/reporting/audit`: independent event consumers.
- `api-gateway`: controlled edge and routing.
- `legacy-integration-service`: slow/failing SOAP-style dependency simulator.
- `infra`: PostgreSQL, Redis, Kafka, Prometheus, Grafana, Loki, Zipkin, Toxiproxy.
- `scripts/failures`: reproducible incident drills.
- `docs`: diagrams, ADRs, SLOs, security, testing, runbook, interview mode.

## Credibility rule
All SLO and scale figures in this playground are design targets, not claims about a real employer or historical platform. Explain what you personally implemented, what is simulated, and what you would add for regulated production.


## Enterprise documentation suite

Start with [`docs/README.md`](docs/README.md). The suite contains architecture, service, flow, database, Kafka, Redis, security, observability, troubleshooting, deployment, ADR, incident, system-design, developer, interview, cheat-sheet, and learning-roadmap documentation.
