# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar file into the container at /app
COPY target/rabbitmqProject-1.0-SNAPSHOT.jar /app/rabbitmqProject-1.0-SNAPSHOT.jar

# Define environment variable for CloudAMQP URL
ENV CLOUDAMQP_URL=amqps://rakkoayb:bLYhhjRY66XIbXOhGSCTNWMX5S1RHzhS@armadillo.rmq.cloudamqp.com/rakkoayb

# Expose the port for the health check endpoint
EXPOSE 8080

# Add Docker health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:8080/health || exit 1

# Run the jar file
ENTRYPOINT ["java", "-jar", "rabbitmqProject-1.0-SNAPSHOT.jar"]