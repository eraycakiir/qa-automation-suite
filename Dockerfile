FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

COPY . .

# Allure kurulumu
RUN apt-get update && apt-get install -y wget unzip zip && \
    wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip && \
    unzip allure-2.24.0.zip && mv allure-2.24.0 /opt/allure && \
    ln -s /opt/allure/bin/allure /usr/bin/allure && \
    rm -rf /var/lib/apt/lists/* allure-2.24.0.zip

CMD ["sh"]
