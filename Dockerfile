FROM openjdk:21-jdk-slim-buster

WORKDIR /app

COPY target/WEB-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9494

CMD [ "java" , "-jar" , "app.jar" ]