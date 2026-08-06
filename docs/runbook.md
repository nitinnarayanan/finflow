# Production Runbook
1. Establish customer impact and financial correctness risk.
2. Check recent deploy, config, schema, and feature-flag changes.
3. Use RED and saturation metrics to localize the failing boundary.
4. Preserve evidence: correlation IDs, traces, thread/heap dumps, deadlock graphs, Kafka offsets.
5. Apply the lowest-risk mitigation before perfect diagnosis.
6. Reconcile all ambiguous or intermediate payment states.
7. Record timeline, decisions, owners, and customer communication.
8. Complete a blameless review with prevention, detection, and recovery actions.
