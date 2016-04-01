#!/usr/bin/env bash

ps -ef | grep java | grep smartee | awk {'print "kill -9 " $2'} | sh -x