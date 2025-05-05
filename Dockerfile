FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

RUN apt-get update && apt-get install -y \
    wget gnupg unzip zip libnss3 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
    libxcomposite1 libxrandr2 libxdamage1 libxkbcommon0 libgbm1 \
    libasound2 libxshmfence1 libgtk-3-0 libx11-xcb1 libdrm2 && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY . .

# ❗️ARG ve ENV kısmı çıkarıldı
RUN rm -rf allure-results/* && \
    mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dgroups=$TEST_GROUP -DconfigFile=config.properties

# Çalıştırılacak test grubu
ARG TEST_GROUP=smoke
ENV TEST_GROUP=${TEST_GROUP}

# Testleri çalıştır (🔧 DÜZELTİLDİ: -Dgroups)
RUN rm -rf allure-results/* && \
    mvn clean test -Dsurefire.suiteXmlFiles=testng.xml -Dgroups=${TEST_GROUP} -DconfigFile=config.properties

# ✅ Report generation
FROM openjdk:17-jdk-slim AS report

WORKDIR /app

RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip && \
    unzip allure-2.24.0.zip && mv allure-2.24.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.zip

COPY --from=build /app/allure-results /app/allure-results

# Allure trend için history klasörü varsa taşı
RUN if [ -d /app/allure-results/history ]; then \
      mkdir -p /app/allure-report/history && \
      cp -r /app/allure-results/history/* /app/allure-report/history/; \
    fi

RUN allure generate /app/allure-results --clean -o /app/allure-report && \
    zip -r /app/allure-report.zip /app/allure-report

CMD ["echo", "Allure report generated and zipped."]
