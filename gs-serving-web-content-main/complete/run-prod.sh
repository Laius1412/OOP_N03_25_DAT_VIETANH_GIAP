#!/bin/bash

# Script để chạy ứng dụng với Aiven MySQL (production)
echo "Starting application with Aiven MySQL (production mode)..."

# Kiểm tra biến môi trường
if [ -z "$AIVEN_MYSQL_HOST" ]; then
    echo "Error: AIVEN_MYSQL_HOST environment variable is not set"
    echo "Please set the following environment variables:"
    echo "  AIVEN_MYSQL_HOST"
    echo "  AIVEN_MYSQL_PORT"
    echo "  AIVEN_MYSQL_DATABASE"
    echo "  AIVEN_MYSQL_USERNAME"
    echo "  AIVEN_MYSQL_PASSWORD"
    exit 1
fi

./mvnw spring-boot:run


