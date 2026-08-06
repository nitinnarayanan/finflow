#!/usr/bin/env bash
docker compose stop redis
echo 'Redis stopped. Observe cache fallback, DB load, hit-rate collapse, and graceful degradation.'
