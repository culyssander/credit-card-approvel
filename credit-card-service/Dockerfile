FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
LABEL email="culyssandercentro@gmail.com"

RUN mkdir "credit-card-service"
WORKDIR /home/credit-card-service

EXPOSE 8090

COPY /target/*.jar /home/credit-card-service/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]