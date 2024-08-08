FROM ubuntu:latest
FROM openjdk:17
ADD /target/websocket-demo-0.0.1-SNAPSHOT.jar websocket-demo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","websocket-demo-0.0.1-SNAPSHOT.jar"]