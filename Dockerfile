FROM openjdk:8-jdk-slim
RUN mkdir /app
WORKDIR /app
ENV PORT 8080
COPY target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]