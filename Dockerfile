FROM openjdk:19
COPY ./target/swagger-spring-1.0.0.jar /tmp
WORKDIR /tmp