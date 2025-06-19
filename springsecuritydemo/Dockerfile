# Start with a Maven image to build the project
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy all files to container
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Use a lightweight Java image to run the app
FROM openjdk:17-jdk-slim

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
