#!/usr/bin/env bash
for i in {1..200}; do curl -s 'http://localhost:8090/api/v1/legacy/kyc/demo?delayMs=10000' >/dev/null & done
wait
