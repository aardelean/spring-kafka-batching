#!/bin/sh

export HOST="$(docker node inspect self --format '{{ .Status.Addr  }}')"