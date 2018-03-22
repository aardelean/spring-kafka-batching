#!/bin/sh

HOST="$(docker node inspect self --format '{{ .Status.Addr  }}')"
MONGO_USER=test
MONGO_PWD=test


