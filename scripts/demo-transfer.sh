#!/usr/bin/env bash
set -euo pipefail
KEY=${1:-demo-$(date +%s)}
curl -sS -X POST http://localhost:8084/api/v1/payments -H 'Content-Type: application/json' -H "Idempotency-Key: $KEY" -H "X-Correlation-ID: demo-$KEY" -d '{"sourceAccountId":"11111111-1111-1111-1111-111111111111","destinationAccountId":"22222222-2222-2222-2222-222222222222","amount":25.00,"currency":"USD"}' | jq .
