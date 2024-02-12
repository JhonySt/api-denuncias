FROM openjdk:17-jdk-alpine
COPY target/api-denuncias-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]