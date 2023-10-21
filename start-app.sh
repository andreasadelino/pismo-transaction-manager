#!/bin/bash

# Build the application with ./mvnw package
cd transaction-manager && ./mvnw clean package -Dquarkus.profile=prod -DskipTests

# Build the Docker image
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/transaction-manager-jvm .

# Run Docker Compose to start PostgreSQL and the application
docker-compose up
