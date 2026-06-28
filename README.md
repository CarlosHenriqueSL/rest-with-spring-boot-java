# Rest With Spring Boot Java

Spring Boot 3 REST API for managing people, books, file storage, and CSV/XLSX person imports with MySQL persistence, Flyway migrations, HATEOAS links, and OpenAPI documentation.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)	
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

## Project Architecture

```text
.
|-- README.md                                      # Project documentation
|-- Collections/
|   `-- REST APIs RESTful from 0...json            # Postman collection for local API requests
|-- UploadDir/                                     # Default local file-storage directory
`-- rest-with-spring-boot-java/                    # Maven/Spring Boot application module
    |-- pom.xml                                    # Maven build, dependency, plugin, and Java version configuration
    |-- src/
    |   |-- main/
    |   |   |-- java/br/com/CarlosHenriqueSL/
    |   |   |   |-- Startup.java                   # Spring Boot application entry point
    |   |   |   |-- config/                        # OpenAPI, CORS/content negotiation, and file-storage config
    |   |   |   |-- controllers/                   # REST controllers for people, books, files, and logs
    |   |   |   |-- controllers/docs/              # Swagger/OpenAPI controller contracts
    |   |   |   |-- data/dto/                      # API transfer objects
    |   |   |   |-- exception/                     # Domain exceptions and centralized error handling
    |   |   |   |-- file/importer/                 # CSV/XLSX import abstraction, factory, and implementations
    |   |   |   |-- mapper/                        # Dozer-backed object mapping helper
    |   |   |   |-- model/                         # JPA entities for Person and Book
    |   |   |   |-- repositories/                  # Spring Data JPA repositories
    |   |   |   |-- serialization/converter/       # YAML HTTP message converter
    |   |   |   `-- services/                      # Business logic for CRUD, import, and file storage
    |   |   `-- resources/
    |   |       |-- application.yml                # Runtime application configuration
    |   |       `-- db/migration/                  # Flyway SQL migrations and seed data
    |   `-- test/
    |       |-- java/br/com/CarlosHenriqueSL/
    |       |   |-- config/                        # Test helper configuration
    |       |   |-- integrationtests/              # REST Assured + Testcontainers integration tests
    |       |   |-- repositories/                  # Repository-level tests
    |       |   `-- unittests/                     # Service, mapper, and mock-data unit tests
    |       `-- resources/application.yml          # Test profile-like configuration
    `-- target/                                    # Maven build output
```

## Core Features

- **📚 Book API** - Create, read, update, delete, and paginate book resources through `/api/book/v1`.
- **👤 People API** - Manage people records with CRUD endpoints, paginated listing, name search, and soft-disable support.
- **🔗 HATEOAS responses** - DTOs include hypermedia links for discoverable API navigation.
- **🧾 Multi-format payloads** - Controllers produce and consume JSON, XML, and YAML through Spring MVC content negotiation.
- **📁 File storage endpoints** - Upload one or many files and download stored files from the configured local upload directory.
- **📥 Mass person import** - Import people from `.csv` and `.xlsx` files through `/api/person/v1/massCreation`.
- **🗄️ Database migrations** - Flyway creates and populates `person` and `books` tables.
- **📖 OpenAPI UI** - Springdoc exposes API documentation with Swagger UI at the application root.
- **🧪 Integration coverage** - REST Assured tests use Testcontainers with MySQL 8.0.36.

## Tech Stack & Dependencies

### Core Runtime

| Category | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.4.0 |
| Build Tool | Maven |
| API Layer | Spring Web MVC |
| Persistence | Spring Data JPA, Hibernate |
| Database | MySQL |
| Migrations | Flyway Core, Flyway MySQL |
| Hypermedia | Spring HATEOAS |
| API Docs | Springdoc OpenAPI Starter WebMVC UI 2.7.0 |

### Serialization & Mapping

| Purpose | Dependency |
| --- | --- |
| JSON | Spring Boot/Jackson default web stack |
| XML | `jackson-dataformat-xml` |
| YAML | `jackson-dataformat-yaml` and custom YAML message converter |
| Object mapping | Dozer Mapper 7.0.0 |

### File Import & Processing

| Purpose | Dependency |
| --- | --- |
| CSV import | Apache Commons CSV 1.14.1 |
| XLSX import | Apache POI OOXML 5.5.1 |
| Multipart upload | Spring Web multipart support |

### Testing

| Purpose | Dependency |
| --- | --- |
| Unit and Spring tests | `spring-boot-starter-test` |
| HTTP integration tests | REST Assured |
| Database integration tests | Testcontainers MySQL 1.20.4 |
| Test database image | `mysql:8.0.36` |

## Getting Started

### Prerequisites

- Java Development Kit 21 or newer.
- Apache Maven 3.9.11 or compatible Maven 3.x installation.
- MySQL running locally for application runtime.
- Docker Desktop or another Docker engine for Testcontainers-based integration tests.
- A database named `rest_with_spring_boot_java` for local application startup.

### Installation

1. Clone the repository and enter the Spring Boot module:

```bash
git clone https://github.com/CarlosHenriqueSL/rest-with-spring-boot-java.git
cd rest-with-spring-boot-java/rest-with-spring-boot-java
```

2. Create the local MySQL database:

```sql
CREATE DATABASE rest_with_spring_boot_java;
```

3. Configure the datasource credentials in `src/main/resources/application.yml` or provide Spring Boot environment overrides.

4. Build the project:

```bash
mvn clean package
```

5. Start the API:

```bash
mvn spring-boot:run
```

The application starts on Spring Boot's default port unless overridden:

```text
http://localhost:8080
```

### Environment Variables

The application reads these configuration values from `application.yml`. Spring Boot can override most keys through environment variables using uppercase names with underscores.

| Environment variable | Configuration key | Default value | Purpose |
| --- | --- | --- | --- |
| `FILE_UPLOAD_DIR` | `file.upload-dir` | Value must be defined in `application.yml` | Directory used by file upload/download endpoints. |
| `SPRING_DATASOURCE_URL` | `spring.datasource.url` | `jdbc:mysql://localhost:3306/rest_with_spring_boot_java?useTimezone=true&serverTimezone=UTC` | MySQL JDBC connection URL. |
| `SPRING_DATASOURCE_USERNAME` | `spring.datasource.username` | `root` | MySQL username. |
| `SPRING_DATASOURCE_PASSWORD` | `spring.datasource.password` | Value must be defined in `application.yml` | MySQL password. |
| `CORS_ORIGINPATTERNS` | `cors.originPatterns` | Value must be defined in `application.yml` | Comma-separated allowed CORS origins. |

PowerShell example:

```powershell
$env:FILE_UPLOAD_DIR = ".\UploadDir"
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3306/rest_with_spring_boot_java?useTimezone=true&serverTimezone=UTC"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "<your-mysql-password>"
mvn spring-boot:run
```

Bash example:

```bash
export FILE_UPLOAD_DIR="./UploadDir"
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/rest_with_spring_boot_java?useTimezone=true&serverTimezone=UTC"
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="<your-mysql-password>"
mvn spring-boot:run
```

## Usage & Examples

### Run the Application

```bash
cd rest-with-spring-boot-java
mvn spring-boot:run
```

### Run Tests

```bash
mvn test
```

The Maven Surefire plugin sets:

```text
DOCKER_HOST=npipe:////./pipe/docker_engine
api.version=1.53
```

This is intended for Docker Desktop on Windows and the Testcontainers-based MySQL integration tests.

### Open API Documentation

```text
http://localhost:8080/
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs
```

### People API

List people with pagination:

```bash
curl "http://localhost:8080/api/person/v1?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

Find people by first name:

```bash
curl "http://localhost:8080/api/person/v1/findPeopleByName/Ayrton?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

Create a person:

```bash
curl -X POST "http://localhost:8080/api/person/v1" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "firstName": "Ada",
    "lastName": "Lovelace",
    "address": "London",
    "gender": "Female"
  }'
```

Disable a person:

```bash
curl -X PATCH "http://localhost:8080/api/person/v1/1" \
  -H "Accept: application/json"
```

Mass-create people from CSV or XLSX:

```bash
curl -X POST "http://localhost:8080/api/person/v1/massCreation" \
  -H "Accept: application/json" \
  -F "file=@people.csv"
```

### Books API

List books:

```bash
curl "http://localhost:8080/api/book/v1?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

Create a book:

```bash
curl -X POST "http://localhost:8080/api/book/v1" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "author": "Robert C. Martin",
    "launchDate": "2008-08-01T00:00:00",
    "price": 45.90,
    "title": "Clean Code"
  }'
```

### XML and YAML Responses

Request XML:

```bash
curl "http://localhost:8080/api/book/v1/1" \
  -H "Accept: application/xml"
```

Request YAML:

```bash
curl "http://localhost:8080/api/person/v1/1" \
  -H "Accept: application/x-yaml"
```

### File API

Upload a single file:

```bash
curl -X POST "http://localhost:8080/api/file/v1/uploadFile" \
  -F "file=@example.txt"
```

Upload multiple files:

```bash
curl -X POST "http://localhost:8080/api/file/v1/uploadMultipleFiles" \
  -F "files=@example-1.txt" \
  -F "files=@example-2.txt"
```

Download a file:

```bash
curl -OJ "http://localhost:8080/api/file/v1/downloadFile/example.txt"
```
