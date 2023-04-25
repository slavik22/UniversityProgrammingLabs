# Maven build
FROM maven:3.8.2-jdk-8-slim AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Server execution
FROM openjdk:8

COPY --from=build /home/app/target/*.jar /usr/local/lib/microservice.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","/usr/local/lib/microservice.jar"]
