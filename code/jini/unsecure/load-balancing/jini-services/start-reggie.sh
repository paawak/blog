#!/bin/sh

if [ $# != 1 ] ; then
  echo "Need the port at which to start the Reggie"
  exit 1
fi

echo "Starting Reggie with port  " $1

java -DreggiePort=$1 -Djava.ext.dirs=reggie-libs -Djava.util.logging.config.file=config/logging.properties -jar reggie-libs/start.jar config/start-reggie.config

