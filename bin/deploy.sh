#!/usr/bin/env bash

if [ $# -ne 1 ]; then
    echo $0: usage: ip
    exit 1
fi

mvn clean package
scp target/ee-0.0.1-SNAPSHOT.jar root@$1:/root/Downloads/smartee/
scp -r target/classes root@$1:/root/Downloads/smartee/
scp bin/tcpserver.py root@$1:/root/Downloads/smartee/simpleTcp
scp bin/run_tcpserver.sh root@$1:/root/Downloads/smartee/simpleTcp
scp bin/stop_tcpserver.sh root@$1:/root/Downloads/smartee/simpleTcp