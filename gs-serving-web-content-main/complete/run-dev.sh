#!/bin/bash

# Script để chạy ứng dụng với H2 database (development)
echo "Starting application with H2 database (development mode)..."
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev


