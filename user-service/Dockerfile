FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/user-service-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-XX:+ExitOnOutOfMemoryError","-jar","app.jar"]
