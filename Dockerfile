# =========================================================================
# STAGE 1: Build and Compile the Application
# =========================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set the working directory inside the build container
WORKDIR /app

# Copy the Maven configuration file and download dependencies (cached layer)
COPY pom.xml .
RUN mvc dependency:go-offline -B

# Copy the entire source code folder into the container
COPY src ./src

# Compile the application and package it into an executable JAR file
# Skip unit tests to speed up the automated build pipeline
RUN mvn clean package -DskipTests

# =========================================================================
# STAGE 2: Lightweight Production Runtime Environment
# =========================================================================
FROM eclipse-temurin:17-jre-alpine

# Set up a secure working directory for runtime execution
WORKDIR /app

# Copy only the compiled fat JAR from the build stage container
COPY --from=build /app/target/*.jar app.jar

# Explicitly declare the network port the application listens on
EXPOSE 8080

# Configure the definitive startup entrypoint command for the container
ENTRYPOINT ["java", "-jar", "app.jar"]
