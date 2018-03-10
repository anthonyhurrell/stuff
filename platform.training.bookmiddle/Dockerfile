FROM openjdk:8u111-jdk-alpine

RUN apk --no-cache add curl
RUN apk --no-cache add jq

COPY /build/libs/*.jar app.jar
ADD ./start.sh start.sh

ENTRYPOINT ["sh", "/start.sh"]
