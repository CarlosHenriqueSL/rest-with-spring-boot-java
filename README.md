# REST With Spring Boot Java

[![Continuous Integration and Delivery with GitHub Actions](https://github.com/CarlosHenriqueSL/rest-with-spring-boot-java/actions/workflows/continuous-deployment.yml/badge.svg)](https://github.com/CarlosHenriqueSL/rest-with-spring-boot-java/actions/workflows/continuous-deployment.yml)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-9.1.0-blue?logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)
![AWS ECS](https://img.shields.io/badge/AWS-ECS-FF9900?logo=amazonaws)

A production-oriented REST API built with Java 21 and Spring Boot. The project demonstrates clean backend architecture, database integration, JWT authentication, file processing, email delivery, report generation, automated testing, Docker, and continuous deployment to AWS ECS.

## Overview

This application provides RESTful endpoints for managing people and books, together with supporting features commonly found in real-world backend systems:

- CRUD operations with pagination and sorting
- JWT-based authentication and token refresh
- User and permission management
- HATEOAS-enabled API responses
- JSON, XML, and YAML content negotiation
- CSV and XLSX imports
- CSV, XLSX, and PDF exports
- File upload and download
- Email delivery with optional attachments
- QR code generation support
- MySQL persistence with Flyway migrations
- OpenAPI and Swagger UI documentation
- Unit, repository, and integration tests
- Testcontainers-based database testing
- Docker and Docker Compose support
- Portainer support for container management
- Continuous integration with GitHub Actions
- Docker image publishing to Docker Hub and Amazon ECR
- Continuous deployment to Amazon ECS

## Main Features

### People API

The People API supports:

- Create, retrieve, update, and delete people
- Pagination and sorting
- Search by first name
- Soft-disable operations
- HATEOAS links
- CSV, XLSX, and PDF exports
- CSV and XLSX imports
- JSON, XML, and YAML responses

Base path:

```text
/api/person/v1
```

### Books API

The Books API supports:

- Create, retrieve, update, and delete books
- Pagination and sorting
- HATEOAS links
- JSON, XML, and YAML responses

Base path:

```text
/api/book/v1
```

### Authentication and Authorization

The application uses Spring Security with JWT tokens.

Available authentication operations include:

- User creation
- Login
- Access-token generation
- Token refresh
- User and permission persistence

Base path:

```text
/auth
```

### File API

The File API supports:

- Single-file uploads
- Multiple-file uploads
- File downloads
- Configurable local file storage

Base path:

```text
/api/file/v1
```

### Email API

The Email API supports:

- Sending simple emails
- Sending emails with file attachments

Base path:

```text
/api/email/v1
```

### Database Migrations

Flyway manages the database schema and seed data, including:

- People
- Books
- Person/book relationships
- Permissions
- Users
- User/permission relationships

Migrations are located at:

```text
rest-with-spring-boot-java/src/main/resources/db/migration
```

## Technology Stack

| Category | Technology |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.4.1 |
| Build tool | Maven |
| Web layer | Spring Web MVC |
| Security | Spring Security and Auth0 Java JWT |
| Persistence | Spring Data JPA and Hibernate |
| Database | MySQL |
| Database migrations | Flyway |
| Hypermedia | Spring HATEOAS |
| API documentation | Springdoc OpenAPI |
| Object mapping | Dozer Mapper |
| JSON/XML/YAML | Jackson |
| CSV processing | Apache Commons CSV |
| XLSX processing | Apache POI |
| PDF reports | JasperReports |
| QR codes | ZXing |
| Email | Spring Boot Mail |
| Integration testing | REST Assured |
| Test database | Testcontainers MySQL |
| Containers | Docker and Docker Compose |
| Container management | Portainer |
| CI/CD | GitHub Actions |
| Container registry | Docker Hub and Amazon ECR |
| Cloud deployment | Amazon ECS |

## Project Structure

```text
.
├── .github/
│   └── workflows/
│       └── continuous-deployment.yml
├── Collections/
│   └── REST APIs RESTful from 0 with Java, Spring Boot, Kubernetes and Docker.postman_collection.json
├── docker-compose.yml
├── README.md
└── rest-with-spring-boot-java/
    ├── Dockerfile
    ├── pom.xml
    └── src/
        ├── main/
        │   ├── java/br/com/CarlosHenriqueSL/
        │   │   ├── config/              # Application, security, CORS, email, and OpenAPI configuration
        │   │   ├── controllers/         # REST controllers
        │   │   ├── data/dto/             # Request and response DTOs
        │   │   ├── exception/            # Exceptions and centralized error handling
        │   │   ├── file/exporter/        # CSV, XLSX, and PDF exporters
        │   │   ├── file/importer/        # CSV and XLSX importers
        │   │   ├── mail/                 # Email sending abstraction
        │   │   ├── mapper/                # Entity and DTO mapping
        │   │   ├── model/                 # JPA entities
        │   │   ├── repositories/          # Spring Data repositories
        │   │   ├── security/jwt/           # JWT provider and authentication filter
        │   │   └── services/               # Business logic
        │   └── resources/
        │       ├── application.yml
        │       ├── db/migration/
        │       └── templates/              # JasperReports templates
        └── test/
            ├── java/
            │   ├── integrationtests/
            │   ├── repositories/
            │   └── unittests/
            └── resources/
                └── application.yml
```

## Prerequisites

Install the following tools before running the project locally:

- Java Development Kit 21
- Maven 3.9 or newer
- MySQL 8 or newer
- Docker Desktop or another Docker-compatible engine
- Git

Docker is required for the Testcontainers-based integration tests and Docker Compose workflow.

## Running Locally with Maven

### 1. Clone the repository

```bash
git clone https://github.com/CarlosHenriqueSL/rest-with-spring-boot-java.git
cd rest-with-spring-boot-java/rest-with-spring-boot-java
```

### 2. Create the database

```sql
CREATE DATABASE rest_with_spring_boot_java;
```

### 3. Configure environment variables

The application reads configuration from:

```text
src/main/resources/application.yml
```

At minimum, configure the datasource password and JWT secret:

```bash
export SPRING_DATASOURCE_USERNAME="root"
export SPRING_DATASOURCE_PASSWORD="your-mysql-password"
export JWT_SECRET="your-development-jwt-secret"
```

Optional configuration:

```bash
export SERVER_PORT="8080"
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/rest_with_spring_boot_java?useTimezone=true&serverTimezone=UTC"
export FILE_UPLOAD_DIR="./UploadDir"
export EMAIL_USERNAME="your-email@example.com"
export EMAIL_PASSWORD="your-email-password"
export CORS_ORIGINPATTERNS="http://localhost:3000,http://localhost:8080"
```

For PowerShell:

```powershell
$env:SERVER_PORT = "8080"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "your-mysql-password"
$env:JWT_SECRET = "your-development-jwt-secret"
$env:FILE_UPLOAD_DIR = ".\UploadDir"
```

### 4. Build the project

```bash
mvn clean package
```

### 5. Start the application

The default application port is `80`.

```bash
mvn spring-boot:run
```

For local development without administrator privileges, use port `8080`:

```bash
SERVER_PORT=8080 mvn spring-boot:run
```

The API will then be available at:

```text
http://localhost:80
```

or:

```text
http://localhost:8080
```

## Running with Docker Compose

The repository includes a Docker Compose configuration with:

- MySQL
- The Spring Boot API
- Portainer

The MySQL container is exposed on port `3308`, while the API is exposed on port `80`.

### 1. Create a `.env` file

Create a `.env` file in the repository root:

```env
MYSQL_ROOT_PASSWORD=change-this-root-password
MYSQL_USER=docker
MYSQL_PASSWORD=change-this-database-password
```

### 2. Start the services

```bash
docker compose up --build
```

### 3. Stop the services

```bash
docker compose down
```

To remove the persisted Portainer volume as well:

```bash
docker compose down -v
```

The services are available at:

| Service | URL |
| --- | --- |
| REST API | `http://localhost` |
| MySQL | `localhost:3308` |
| Portainer | `http://localhost:9000` |

The Docker Compose network allows the application to connect to MySQL using the service name:

```text
jdbc:mysql://db:3306/rest_with_spring_boot_java
```

## API Documentation

After starting the application, OpenAPI documentation is available at:

```text
http://localhost/swagger-ui/index.html
```

The raw OpenAPI specification is available at:

```text
http://localhost/v3/api-docs
```

The Swagger UI is also configured to use the root path:

```text
http://localhost/
```

If the application is running on port `8080`, use:

```text
http://localhost:8080/swagger-ui/index.html
```

## API Examples

The examples below assume that the application is running on port `80`.

### Create a user

```bash
curl -X POST "http://localhost/auth/createUser" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "username": "admin",
    "password": "change-me"
  }'
```

### Sign in

```bash
curl -X POST "http://localhost/auth/signin" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "username": "admin",
    "password": "change-me"
  }'
```

The response contains a JWT token. Use that token in the `Authorization` header when calling protected endpoints:

```text
Authorization: Bearer <your-jwt-token>
```

### Refresh a token

```bash
curl -X PUT "http://localhost/auth/refresh/admin" \
  -H "Authorization: Bearer <your-refresh-token>"
```

### List people

```bash
curl "http://localhost/api/person/v1?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

### Find a person by ID

```bash
curl "http://localhost/api/person/v1/1" \
  -H "Accept: application/json"
```

### Search people by first name

```bash
curl "http://localhost/api/person/v1/findPeopleByName/Ayrton?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

### Create a person

```bash
curl -X POST "http://localhost/api/person/v1" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "firstName": "Ada",
    "lastName": "Lovelace",
    "address": "London",
    "gender": "Female"
  }'
```

### Disable a person

```bash
curl -X PATCH "http://localhost/api/person/v1/1" \
  -H "Accept: application/json"
```

### Import people from CSV or XLSX

```bash
curl -X POST "http://localhost/api/person/v1/massCreation" \
  -H "Accept: application/json" \
  -F "file=@people.csv"
```

### Export a page of people as CSV

```bash
curl "http://localhost/api/person/v1/exportPage?page=0&size=12&direction=asc" \
  -H "Accept: text/csv" \
  -o people.csv
```

### Export a page of people as XLSX

```bash
curl "http://localhost/api/person/v1/exportPage?page=0&size=12&direction=asc" \
  -H "Accept: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" \
  -o people.xlsx
```

### Export a page of people as PDF

```bash
curl "http://localhost/api/person/v1/exportPage?page=0&size=12&direction=asc" \
  -H "Accept: application/pdf" \
  -o people.pdf
```

### List books

```bash
curl "http://localhost/api/book/v1?page=0&size=12&direction=asc" \
  -H "Accept: application/json"
```

### Create a book

```bash
curl -X POST "http://localhost/api/book/v1" \
  -H "Content-Type: application/json" \
  -H "Accept: application/json" \
  -d '{
    "author": "Robert C. Martin",
    "launchDate": "2008-08-01T00:00:00",
    "price": 45.90,
    "title": "Clean Code"
  }'
```

### Request an XML response

```bash
curl "http://localhost/api/book/v1/1" \
  -H "Accept: application/xml"
```

### Request a YAML response

```bash
curl "http://localhost/api/person/v1/1" \
  -H "Accept: application/x-yaml"
```

### Upload a file

```bash
curl -X POST "http://localhost/api/file/v1/uploadFile" \
  -F "file=@example.txt"
```

### Upload multiple files

```bash
curl -X POST "http://localhost/api/file/v1/uploadMultipleFiles" \
  -F "files=@example-1.txt" \
  -F "files=@example-2.txt"
```

### Download a file

```bash
curl -OJ "http://localhost/api/file/v1/downloadFile/example.txt"
```

### Send a simple email

```bash
curl -X POST "http://localhost/api/email/v1" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "recipient@example.com",
    "subject": "Test email",
    "body": "This is a test message."
  }'
```

### Send an email with an attachment

```bash
curl -X POST "http://localhost/api/email/v1/withAttachment" \
  -F 'emailRequest={"to":"recipient@example.com","subject":"Report","body":"Attached is the requested report."}' \
  -F "attachment=@report.pdf"
```

## Configuration Reference

The main configuration file is:

```text
rest-with-spring-boot-java/src/main/resources/application.yml
```

Important environment variables include:

| Variable | Purpose |
| --- | --- |
| `SERVER_PORT` | Overrides the HTTP server port |
| `SPRING_DATASOURCE_URL` | MySQL JDBC connection URL |
| `SPRING_DATASOURCE_USERNAME` | MySQL username |
| `SPRING_DATASOURCE_PASSWORD` | MySQL password |
| `JWT_SECRET` | Secret used to sign JWT tokens |
| `FILE_UPLOAD_DIR` | Directory used for uploaded files |
| `EMAIL_USERNAME` | SMTP username |
| `EMAIL_PASSWORD` | SMTP password |
| `CORS_ORIGINPATTERNS` | Comma-separated allowed CORS origins |

The default configuration includes:

- HTTP port: `80`
- Database: `rest_with_spring_boot_java`
- Upload directory: `./UploadDir`
- SMTP provider: Gmail SMTP
- Maximum file size: `200 MB`
- Maximum multipart request size: approximately `215 MB`

## Running Tests

Run the complete test suite from the application module:

```bash
cd rest-with-spring-boot-java
mvn test
```

The test suite includes:

- Unit tests for services and mappers
- Repository tests
- REST controller integration tests
- JSON, XML, and YAML response tests
- CORS tests
- Authentication tests
- Swagger/OpenAPI integration tests
- Testcontainers-based MySQL tests

Docker must be available when running integration tests that use Testcontainers.

To generate a packaged application:

```bash
mvn clean package
```

## Docker Image

The application Dockerfile uses Eclipse Temurin Java 21:

```dockerfile
FROM eclipse-temurin:21-jdk
```

Build the image manually:

```bash
cd rest-with-spring-boot-java
mvn clean package
docker build -t rest-with-spring-boot-java .
```

Run the container:

```bash
docker run --rm \
  -p 80:80 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/rest_with_spring_boot_java?useTimezone=true&serverTimezone=UTC" \
  -e SPRING_DATASOURCE_USERNAME="root" \
  -e SPRING_DATASOURCE_PASSWORD="your-mysql-password" \
  -e JWT_SECRET="your-development-jwt-secret" \
  rest-with-spring-boot-java
```

## Continuous Integration and Deployment

The GitHub Actions workflow is located at:

```text
.github/workflows/continuous-deployment.yml
```

The pipeline performs the following tasks:

1. Checks out the repository.
2. Logs in to Docker Hub.
3. Configures AWS credentials.
4. Authenticates with Amazon ECR.
5. Sets up Java 21 and Maven caching.
6. Verifies Docker availability.
7. Builds and tests the Spring Boot application.
8. Uploads Surefire reports when the build fails.
9. Builds the Docker Compose image.
10. Tags and pushes the image to Amazon ECR.
11. Downloads the Amazon ECS task definition.
12. Updates the ECS task definition with the new image.
13. Deploys the updated task definition to Amazon ECS.
14. Waits for ECS service stability.
15. Pushes versioned and latest images to Docker Hub.

The deployment workflow requires the following GitHub Actions secrets:

```text
AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY
AWS_DEFAULT_REGION
DOCKER_USERNAME
DOCKER_ACCESS_TOKEN
IMAGE_REPO_URL
TASK_DEFINITION_NAME
CONTAINER_NAME
SERVICE_NAME
CLUSTER_NAME
```

The Docker image is tagged with both:

- `latest`
- The GitHub Actions run ID

This allows the deployed image to be identified and traced back to a specific workflow execution.

## Postman Collection

A Postman collection is available in the repository:

```text
Collections/REST APIs RESTful from 0 with Java, Spring Boot, Kubernetes and Docker.postman_collection.json
```

Import this file into Postman to test the available endpoints locally.
