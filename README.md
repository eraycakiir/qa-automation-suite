# 🧪 QA Automation Framework  
**Java + Playwright + TestNG + RestAssured + Docker + GitHub Actions**

This repository contains a robust and scalable test automation framework built for both UI and API testing using Java. It’s designed to simulate real-world scenarios with modern tools, is containerized using Docker, and fully integrated with GitHub Actions for CI/CD.

---

## 🚀 Tech Stack

- **Java 17** – Core language for all tests  
- **Playwright (Java)** – Browser automation for UI testing  
- **TestNG** – Testing framework for structure and grouping  
- **RestAssured** – API testing support  
- **Allure** – Reporting system  
- **Docker** – Containerized execution  
- **GitHub Actions** – Continuous integration and test automation

---
## 📁 Project Structure

```
src
└── main/java
    ├── pages/          → Page Object Models for UI testing
    ├── tests/ui/       → UI test classes grouped by functionality
    ├── tests/api/      → API test classes with CRUD operations
    ├── base/           → Base test classes for UI and API (e.g., BaseTest, BaseApiTest)
    └── utils/          → Utility classes such as ApiUtils
```

All environment configurations (e.g., URLs, credentials) are managed in the `config.properties` file and can be overridden via Maven command line.

---

## ✅ How to Run Tests

### ▶️ Run All Tests Locally

```bash
mvn clean test -Dsurefire.suiteXmlFiles=testng.xml
```

### 🎯 Run a Specific Test Group

```bash
mvn clean test -Dgroups=smoke
```

---

## 🐳 Run with Docker

### 🛠️ Build the Image

```bash
docker build -t qa-tests .
```

### 🚀 Run the Tests

```bash
docker run --name run-tests qa-tests
```

### 📦 Export the Allure Report

```bash
docker cp run-tests:/app/allure-report.zip .
```

### 🎛️ Optional: Run with Group Filtering

```bash
docker build --build-arg TEST_GROUP=regression -t qa-tests .
```

---

## ⚙️ GitHub Actions – CI/CD Integration

This framework supports manual test execution via GitHub Actions using `workflow_dispatch`. You can select the test group to run directly from the GitHub UI.

```yaml
on:
  workflow_dispatch:
    inputs:
      group:
        description: 'Test group to run (e.g. smoke, regression)'
        required: true
```

Test results are generated inside the Docker container and exported as a zipped Allure report.

---

## 📊 Allure Reports

To view reports locally:

```bash
allure serve allure-results
```

Or unzip the generated `allure-report.zip` from Docker:

```bash
unzip allure-report.zip && open allure-report/index.html
```
---
