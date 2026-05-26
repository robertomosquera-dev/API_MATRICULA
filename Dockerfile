FROM eclipse-temurin:25-jdk-alpine

LABEL authors="rober"

WORKDIR /root

COPY pom.xml /root
COPY .mvn /root/.mvn
COPY mvnw /root

RUN ./mvnw dependency:go-offline

COPY ./src /root/src

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

ENTRYPOINT ["top", "-b"]