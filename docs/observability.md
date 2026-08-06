# Observability

Use metrics to identify systemic patterns, traces to localize a path, and logs for detailed evidence. Required dimensions: service, endpoint, outcome, dependency, region; never put account IDs in metric labels.

## SLIs
- Correct transfer success / valid attempts
- p95 and p99 API latency
- Oldest pending outbox age
- Kafka event age and consumer lag
- Stuck transfer count by state
- DB and HTTP pool saturation
- Cache hit rate, stale age, fallback DB load
- Fraud degraded-mode volume

## Example SLOs (playground targets, not historical claims)
- 99.95% correct transfer processing per month
- p95 below 400 ms excluding external settlement
- 99.9% of outbox events published within 60 seconds
