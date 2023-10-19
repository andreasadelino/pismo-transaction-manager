#!/bin/bash

# Build the application with ./mvnw package
cd transaction-manager && ./mvnw package -Dquarkus.profile=dev -DskipTests

# Build the Docker image
docker build -t quarkus/transaction-manager-jvm .

# Run Docker Compose to start PostgreSQL and the application
docker-compose up
