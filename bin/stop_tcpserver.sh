#!/usr/bin/env bash
ps -ef | grep tcpserver.py | awk {'print "kill -9 " $2'} | sh -x