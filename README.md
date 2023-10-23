# pismo-transaction-manager
Technical test requested for Pismo.

## Requirements

To set up the server on your machine, you'll need:

- Docker;
- Docker Compose version 2+;
- Java SDK 17+
- SO ports **5433** and **9092** available;
- Access to terminal on Linux or macOS;

## How to start up the production server
- Use the terminal to go to the root directory and execute: 
```shell
./start-app.sh
```
The shell script will build the application jar and start up the Docker containers (PostgreSQL and application backend). The backend exposes port 9092 and the database port 5433.

## OpenAPI specification
- http://localhost:9092/q/openapi

## How to call the APIs

- Call requests to **http://localhost:9092/{uri}**
