FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
RUN mkdir "authentication-service"
WORKDIR /home/authentication-service

EXPOSE 9595

COPY /target/*.jar /home/authentication-service/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]