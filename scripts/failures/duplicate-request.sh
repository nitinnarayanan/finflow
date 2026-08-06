#!/usr/bin/env bash
KEY=duplicate-lab
for i in {1..10}; do ./scripts/demo-transfer.sh "$KEY" & done
wait
echo 'Verify exactly one payment row and consistent responses.'
