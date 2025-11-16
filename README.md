# UI Automated Testing Project for OTUS

## Overview
Automated UI tests for the OTUS learning platform (https://otus.ru) using Selenium WebDriver with Java. Implements Page Object Model with Google Guice DI.

## Technology Stack
- Java 24
- Selenium WebDriver 4.36.0
- JUnit Jupiter 5.10.2
- WebDriverManager 6.3.2
- Google Guice 7.0.0
- AssertJ 3.27.6
- JSoup 1.17.2
- Maven 3.14.0

## Project Structure
```
project/
├── src/
│   ├── main/java/
│   │   ├── pages/         # Page Object classes
│   │   ├── extensions/    # JUnit extensions
│   │   ├── annotations/   # Custom annotations
│   │   ├── dto/           # Data Transfer Objects
│   │   └── waiters/       # Custom wait conditions
│   └── test/java/
│       └── otus/          # Test scenarios
└── pom.xml
```

## Test Scenarios
1. **scenario1** — Verify course search by exact name
2. **scenario2** — Find earliest and latest courses
3. **scenario3** — Navigate categories and validate selection

## Setup and Configuration

### Prerequisites
- JDK 24
- Maven 3.x
- Chrome browser (default)

### Configuration Properties
In `pom.xml`:
- `base.url` (default: https://otus.ru)
- `browser.name` (default: chrome)

## Running Tests
```bash
# all tests
mvn clean test

# with profile
mvn clean test -Pprod

# specific test
mvn clean test -Dtest=scenario1
```
