# ADR-0002: Transactional outbox
Status: Accepted

Business state and event intent commit in one database transaction. A relay publishes later. This removes the crash gap between DB commit and Kafka publish. It does not create exactly-once external effects; consumers remain idempotent and reconciliation remains mandatory.
