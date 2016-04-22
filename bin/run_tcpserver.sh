#!/usr/bin/env bash
if [ $# -ne 1 ]; then
    echo $0: usage: port
    exit 1
fi

python tcpserver.py $1 &