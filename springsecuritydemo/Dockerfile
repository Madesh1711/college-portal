# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build file
COPY pom.xml .

# Copy all source files
COPY src ./src

# Copy any other required files like application.properties
COPY . .

# Package the application
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot app
CMD ["java", "-jar", "target/*.jar"]
