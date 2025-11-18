FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

RUN apk --no-cache add maven

ENV DB_HOST=${DB_HOST}
ENV DB_NAME=${DB_NAME}
ENV DB_USER=${DB_USER}
ENV DB_PASS=${DB_PASS}

WORKDIR /Urzapp

# COPY urzasoracle/target/urzasoracle-0.0.1-SNAPSHOT.jar app.jar
COPY urzasoracle/pom.xml .

RUN mvn dependency:resolve

COPY urzasoracle/src src

RUN mvn package -DskipTests

EXPOSE 8080

ENTRYPOINT [ "java","-jar", "target/urza.jar" ]