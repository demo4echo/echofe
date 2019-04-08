FROM openjdk:8-jre-alpine

ARG PRODUCT_NAME
ARG PRODUCT_VERSION

RUN addgroup echogroup && adduser -D -H echouser -G echogroup

USER echouser

WORKDIR /opt/com.mavenir/demo/echo

COPY ./build/libs/$PRODUCT_NAME-$PRODUCT_VERSION.jar ./

ENTRYPOINT ["java","-jar","$PRODUCT_NAME-$PRODUCT_VERSION.jar"]

EXPOSE 9999

LABEL maintainer="tiran.meltser@mavenir.com"
