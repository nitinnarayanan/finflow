#!/usr/bin/env bash
docker compose stop postgres
echo 'PostgreSQL stopped. Expect readiness failure, connection errors, and payment rejection.'
