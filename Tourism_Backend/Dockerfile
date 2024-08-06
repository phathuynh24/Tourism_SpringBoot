# Use an official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Create a volume to store the temporary files
VOLUME /tmp

# Copy the jar file into the container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
