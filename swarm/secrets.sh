#!/bin/sh

echo "postgres" | docker secret create pg_password - &&\
echo "postgres" | docker secret create pg_user - &&\
echo "batch" | docker secret create pg_db -