# pismo-transaction-manager
Technical test requested for Pismo.

## Requirements

To set up the server on your machine, you'll need:

- Docker;
- Docker Compose version 2+;
- Java SDK 17+
- SO ports **5433** and **8080** available;
- Access to terminal on Linux or macOS;

## Steps to start up the server
- Use the terminal to go the root directory and execute: 
```shell
./start-app.sh
```
The shell script will build the application jar as well the Docker services (PostgreSQL and application backend).
