# 4. Microservice Documentation


> **Documentation status model**
>
> * **Implemented:** observable in the current repository.
> * **Target production design:** the enterprise behavior this playground is designed to teach.
> * **Planned lab:** intentionally left as an extension exercise.
>
> Numerical scale, latency, and availability values are **illustrative engineering targets**, not claims about a real employer or historical production system.

## Service map

* [API Gateway](services/api-gateway.md)
* [Authentication Service](services/auth-service.md)
* [User Service](services/user-service.md)
* [Account Service](services/account-service.md)
* [Payment Service](services/payment-service.md)
* [Fraud Detection Service](services/fraud-service.md)
* [Transaction Service](services/transaction-service.md)
* [Notification Service](services/notification-service.md)
* [Reporting Service](services/reporting-service.md)
* [Audit Service](services/audit-service.md)
* [Legacy Integration Service](services/legacy-integration-service.md)
* [Configuration Service](services/configuration-service.md)

## Shared service standards

Every production service must define an owner, API/event contract, SLO, dependency budget, data classification, dashboard, alerts, on-call runbook, capacity model, deployment strategy, and deprecation path. Stateless HTTP services scale horizontally; stateful bottlenecks are managed at database, broker, cache, and external-provider boundaries.
