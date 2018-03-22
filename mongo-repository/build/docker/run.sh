#!/bin/bash

for file in run/secrets/*; do
	filename=$(basename "$file")
	variable="${filename^^}"
    export $variable=$(cat $file)
done
exec java ${JAVA_OPTS} -server -jar /opt/mongo-repository.jar
