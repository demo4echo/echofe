FROM openjdk:8-jre-alpine

ARG JAR_PRODUCT_NAME
ARG JAR_PRODUCT_VERSION

WORKDIR /opt/com.mavenir/demo-echo/echofe

COPY ./build/libs/$JAR_PRODUCT_NAME-$JAR_PRODUCT_VERSION.jar ./

RUN addgroup echogroup && \
	adduser -D -H echouser -G echogroup && \
	ln -s ./$JAR_PRODUCT_NAME-$JAR_PRODUCT_VERSION.jar ./echofe.jar

USER echouser

ENTRYPOINT ["java","-jar","./echofe.jar"]

EXPOSE $PRODUCT_INTERNAL_PORT

LABEL maintainer="tiran.meltser@efrat.com"
