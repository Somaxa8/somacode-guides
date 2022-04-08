#!/usr/bin/env bash

docker stop guides
docker rm guides
docker rmi guides

cd /root/guides/project
bash -x gradlew buildDocker --no-daemon --stacktrace -Dprod -Pprofile=prod -x test -Dkotlin.compiler.execution.strategy=in-process -Dkotlin.incremental=false

docker logs -f guides