FROM maven:3.9.8-eclipse-temurin-21-alpine AS build

RUN apk --no-cache add maven


WORKDIR /Urzapp

# COPY urzasoracle/target/urzasoracle-0.0.1-SNAPSHOT.jar app.jar
COPY urzasoracle/pom.xml .

RUN mvn dependency:resolve

COPY urzasoracle/src src

RUN mvn package -DskipTests

EXPOSE 8080

ENTRYPOINT [ "java","-jar", "target/urza.jar" ]