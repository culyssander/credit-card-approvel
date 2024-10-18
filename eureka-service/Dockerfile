FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
LABEL email="culyssandercentro@gmail.com"

RUN mkdir "eureka-service"
WORKDIR /home/eureka-service

EXPOSE 8761

COPY /target/*.jar /home/eureka-service/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]