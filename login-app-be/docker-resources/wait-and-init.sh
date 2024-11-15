#!/bin/sh

echo 'Starting Backend...'
# wait the inizialization of the database
sleep 30

JAVA_OPTS=""

java -jar /app.jar $JAVA_OPTS