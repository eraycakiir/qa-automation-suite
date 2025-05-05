# ✅ Dockerfile
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Gerekli sistem bağımlılıkları
RUN apt-get update && apt-get install -y \
    wget unzip zip gnupg libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libxcomposite1 libxrandr2 libxdamage1 libxkbcommon0 libgbm1 libasound2 \
    libxshmfence1 libgtk-3-0 libx11-xcb1 libdrm2 && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .
RUN rm -rf allure-results && mkdir allure-results && \
    mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -DconfigFile=/app/config.properties

FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.tgz && \
    tar -xzf allure-2.24.0.tgz && mv allure-2.24.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.tgz

COPY --from=build /app/allure-results /app/allure-results

CMD allure generate /app/allure-results --clean -o /app/allure-report && \
    zip -r /app/allure-report.zip /app/allure-report
