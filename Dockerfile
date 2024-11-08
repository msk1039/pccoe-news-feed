# Build stage
FROM eclipse-temurin:17-jdk-focal as builder

WORKDIR /app

# Copy the Maven POM file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Copy the Maven wrapper files
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Make the Maven wrapper executable
RUN chmod +x mvnw

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# Create certificates directory
RUN mkdir -p /app/certs

# Copy the certificate and conversion script
COPY certs/ca.pem /app/certs/
COPY create-truststore.sh /app/
RUN chmod +x /app/create-truststore.sh

# Copy the built JAR file from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Convert the certificate and run the application
ENTRYPOINT ["/bin/sh", "-c", "/app/create-truststore.sh && java -Djavax.net.ssl.trustStore=/app/certs/truststore.jks -Djavax.net.ssl.trustStorePassword=truststore -jar app.jar"]

EXPOSE 8080