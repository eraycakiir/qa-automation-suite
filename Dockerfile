#############################################
#           1. Build                       #
#############################################
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# System library (For Playwright support)
RUN apt-get update && apt-get install -y \
    wget gnupg unzip zip libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libxcomposite1 libxrandr2 libxdamage1 libxkbcommon0 libgbm1 libasound2 \
    libxshmfence1 libgtk-3-0 libx11-xcb1 libdrm2 && \
    rm -rf /var/lib/apt/lists/*

# Maven dependency download
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy resources
COPY . .

# Playwright browser dowmload
RUN mvn -B exec:java \
    -Dexec.mainClass=com.microsoft.playwright.CLI \
    -Dexec.args="install"

# Remowe old allure results
RUN rm -rf allure-results

# Run Test
ARG groups=
RUN if [ -z "$groups" ]; then \
      echo "🚀 Running all tests (no group filter)" && \
      mvn -B clean test; \
    else \
      echo "🎯 Running group: $groups" && \
      mvn -B clean test -Dgroups=$groups; \
    fi

#############################################
#           2. Reporting Process            #
#############################################
FROM openjdk:17-jdk-slim AS report
WORKDIR /app

# Allure CLI
RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip && \
    unzip allure-2.24.0.zip && mv allure-2.24.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.zip

# Allure result and create report
COPY --from=build /app/allure-results /app/allure-results
RUN allure generate /app/allure-results --clean -o /app/allure-report && \
    zip -r /app/allure-report.zip /app/allure-report

CMD ["echo", "✅ Allure report generated and zipped."]