# 🧪 Stage 1 - Build & Test
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget unzip zip libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 libxcomposite1 \
    libxrandr2 libxdamage1 libxkbcommon0 libgbm1 libasound2 libxshmfence1 \
    libgtk-3-0 libx11-xcb1 libdrm2 && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .

# ⛔ Clear previous results (just in case)
RUN rm -rf /app/allure-results/* || true

# 🧪 Run tests
RUN mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -DconfigFile=/app/config.properties

# 🧪 Stage 2 - Generate Allure Report
FROM openjdk:17-jdk-slim

WORKDIR /app

# Allure setup
RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.tgz && \
    tar -xzf allure-2.24.0.tgz && mv allure-2.24.0 /opt/allure && ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.tgz

# Copy test results
COPY --from=build /app/target/surefire-reports /app/allure-results

# 🧾 Inject environment.properties
RUN mkdir -p /app/allure-results && \
    echo "Environment=CI" >> /app/allure-results/environment.properties && \
    echo "Executed By=GitHub Actions" >> /app/allure-results/environment.properties && \
    echo "Project Name=QA Automation Suite" >> /app/allure-results/environment.properties

# ⚙️ Generate and zip report
CMD allure generate /app/allure-results --clean -o /app/allure-report && \
    zip -r /app/allure-report.zip /app/allure-report
