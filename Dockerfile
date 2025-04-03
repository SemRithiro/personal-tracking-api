# Use an Alpine image and install OpenJDK manually
FROM alpine:latest

# Install OpenJDK 21 (or any version you need)
RUN apk add --no-cache openjdk21-jre

WORKDIR /app
COPY target/personaltracking-1.0.0.jar app.jar

CMD [ "java", "-Xms16m", "-Xmx32m", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:MaxRAMPercentage=80", "-jar", "/app/app.jar" ]
