FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
RUN mkdir "api-gateway"

WORKDIR /home/api-gateway

EXPOSE 8888

COPY /target/*.jar /home/api-gateway/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]