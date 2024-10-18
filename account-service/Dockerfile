FROM amazoncorretto:17.0.7-alpine

LABEL authors="Quitumba Ferreira"
LABEL email="culyssandercentro@gmail.com"

RUN mkdir "account-service"
WORKDIR /home/account-service

EXPOSE 8085

ENV DATASOURCE_HOST=mysqldb:3306
ENV DATASOURCE_USER=root
ENV DATASOURCE_PASSWORD=root
ENV DATASOURCE_DB=account
ENV SPRING_DATA_SOURCE_URL=jdbc:mysql://mysqldb:3306/account?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=America/Sao_Paulo

COPY /target/*.jar /home/account-service/app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]