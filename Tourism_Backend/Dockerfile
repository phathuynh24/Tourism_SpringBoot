# Use the OpenJDK 21 image with the slim variant
# This is a lightweight Java image, with a smaller size, and includes the necessary tools to run a Java application.
FROM openjdk:21-jdk-slim

# Copy the built .jar file from the 'target' directory in your project to the root directory of the container.
# This .jar file contains all the compiled source code and is ready to be executed.
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run when the container starts.
# Here, the 'java -jar /app.jar' command will be executed to run the Java application from the copied .jar file.
ENTRYPOINT ["java","-jar","/app.jar"]
