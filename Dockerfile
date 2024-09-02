# Stage 1: Build the Spring Boot application
FROM openjdk:11 as build

# Set the working directory in the container
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the Spring Boot application
RUN ./gradlew clean build --no-daemon

# Stage 2: Create a smaller image with just the JAR file
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/build/libs/ifelsejsonparser-*.jar app.jar

# Expose the port that the Spring Boot app will listen on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
