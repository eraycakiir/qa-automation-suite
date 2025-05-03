#!/bin/bash

echo "🔄 Building Docker image..."
docker build -t qa-automation-suite .

echo "▶️ Running tests inside Docker and serving Allure Report on http://localhost:8080 ..."
docker run --rm \
  -p 8080:8080 \
  -v "$(pwd)/allure-results:/app/target/allure-results" \
  qa-automation-suite
