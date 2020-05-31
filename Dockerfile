FROM openjdk:8-jdk-alpine

LABEL maintainer="yurimarx@gmail.com"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]