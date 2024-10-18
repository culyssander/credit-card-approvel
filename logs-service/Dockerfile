FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
LABEL email="culyssandercentro@gmail.com"

RUN mkdir "logs-service"
WORKDIR /home/logs-service

EXPOSE 9095

COPY /target/*.jar /home/logs-service/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]