#!/usr/bin/env bash

export AGENT_PATH="/root/Downloads/smartee/pinpoint/pinpoint-agent"
export MAVEN_OPTS="-javaagent:$AGENT_PATH/pinpoint-bootstrap-1.5.2-SNAPSHOT.jar -Dpinpoint.agentId=smartee -Dpinpoint.applicationName=smartee"
mvn exec:java &