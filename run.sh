#!/bin/bash

DOCKER_IMAGE=markhobson/maven-chrome:jdk-16

docker pull $DOCKER_IMAGE
docker run --rm -it -v "$PWD/mvn_cache":/.mvn -v "$PWD":/usr/src -w /usr/src $DOCKER_IMAGE mvn -Dmaven.repo.local=./mvn_cache test
