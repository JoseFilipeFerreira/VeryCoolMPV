#!/bin/bash
echo '{ "command": ["loadfile", "'"$1"'", "append"]}' \
    | socat - "/tmp/mpvsocket" \
    | jq --raw-output .error
