FROM openjdk:21-jdk-slim

LABEL org.opencontainers.image.authors="belivan84@gmail.com"

COPY target/accounts-*.jar accounts.jar

ENTRYPOINT ["java","-jar","accounts.jar"]