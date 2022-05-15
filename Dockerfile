FROM eclipse-temurin:11-alpine
VOLUME /tmp

ENV DISPLAY host.docker.internal:0.0
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ADD lib lib
ENTRYPOINT ["java", "-jar", "/app.jar"]
