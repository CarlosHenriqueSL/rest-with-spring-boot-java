FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/*.jar app.jar
RUN mkdir -p /app/UploadDir
ENTRYPOINT ["java", "-jar", "app.jar"]