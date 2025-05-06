#############################################
#           1. Build Aşaması               #
#############################################
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Sistem kütüphaneleri (Playwright destek için)
RUN apt-get update && apt-get install -y \
    wget gnupg unzip zip libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libxcomposite1 libxrandr2 libxdamage1 libxkbcommon0 libgbm1 libasound2 \
    libxshmfence1 libgtk-3-0 libx11-xcb1 libdrm2 && \
    rm -rf /var/lib/apt/lists/*

# Bağımlılıkları önceden indir
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Kaynakları kopyala
COPY . .

# Playwright tarayıcılarını indir
RUN mvn -B exec:java \
    -Dexec.mainClass=com.microsoft.playwright.CLI \
    -Dexec.args="install"

# 🔄 Önceki Allure verilerini temizle
RUN rm -rf allure-results

# Testleri çalıştır (grup verilmemişse tüm testleri çalıştır)
ARG groups=all
RUN if [ "$groups" = "all" ]; then \
      mvn -B clean test; \
    else \
      mvn -B clean test -Dgroups=$groups; \
    fi

#############################################
#           2. Raporlama Aşaması            #
#############################################
FROM openjdk:17-jdk-slim AS report
WORKDIR /app

# Allure CLI kurulumu
RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip && \
    unzip allure-2.24.0.zip && mv allure-2.24.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.zip

# Sonuçları kopyala ve rapor üret
COPY --from=build /app/allure-results /app/allure-results
RUN allure generate /app/allure-results --clean -o /app/allure-report && \
    zip -r /app/allure-report.zip /app/allure-report

CMD ["echo", "✅ Allure report generated and zipped."]
