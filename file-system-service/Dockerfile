FROM openjdk:17-alpine
WORKDIR /app
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]