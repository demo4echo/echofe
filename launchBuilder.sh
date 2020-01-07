#!/usr/bin/env sh

docker run -it --rm -v /var/run/docker.sock:/var/run/docker.sock -v `pwd`:/root/repo -w /root/repo demo4echo/alpine_openjdk8_k8scdk:latest ash
