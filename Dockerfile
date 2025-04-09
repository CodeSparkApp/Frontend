# Use an official Maven image to build the project
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Install Node.js (needed by Vaadin for frontend build)
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    node -v && npm -v

# Copy all the code into the image
COPY . .

# Run full Vaadin frontend + backend build
RUN mvn clean install \
    vaadin:prepare-frontend vaadin:build-frontend \
    -DskipTests

# Use JRE image to run the application
FROM eclipse-temurin:21-jre

# Copy the built JAR from the build stage
COPY --from=build /target/*.jar app.jar

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
