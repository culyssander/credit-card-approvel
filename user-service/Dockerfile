FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
RUN mkdir "users-services"
WORKDIR /home/users-services

EXPOSE 8095

COPY /target/*.jar /home/users-services/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]