#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo $0: usage: name
    exit 1
fi

mvn clean package
scp target/ee-0.0.1-SNAPSHOT.jar root@$1:/root/Downloads/smartee/