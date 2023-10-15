# Use the official OpenJDK base image
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file and Gradle wrapper files to the container
COPY . .

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the Spring Boot application with Gradle
RUN ./gradlew build

# Expose the port that the Spring Boot app will listen on
EXPOSE 8080

#Copy the Spring Boot JAR fil
COPY build/libs/ifelsejsonparser-*.jar app.jar

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
