#FROM busybox
#FROM alpine
FROM openjdk:8-jre-alpine
#ADD --chown=root:root server-jre-8u202-linux-x64.tar.gz /opt/jvm/

# Alpine style
RUN addgroup echogroup && adduser -D -H echouser -G echogroup
USER echouser
WORKDIR /opt/com.mavenir/demo/echo
COPY ./build/libs/CI-CD_EchoServerFE-1.0.jar ./
ENTRYPOINT ["java","-jar","CI-CD_EchoServerFE-1.0.jar"]
EXPOSE 9999
LABEL maintainer="tiran.meltser@mavenir.com"
