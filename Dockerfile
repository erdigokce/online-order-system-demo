FROM openjdk:17.0.1-slim-buster

COPY oos-pre-compiled.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]