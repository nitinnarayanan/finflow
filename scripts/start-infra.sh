#!/usr/bin/env bash
set -euo pipefail
docker compose up -d postgres redis kafka zipkin prometheus grafana loki toxiproxy
