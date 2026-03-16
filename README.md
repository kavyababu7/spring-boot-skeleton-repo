# Nissan Spring Boot Starter Template

A production-ready skeleton for backend microservices using Spring Boot 3.4 and Java 21.

## Stack

- **Java 21 & Spring Boot 3.4**: 
- **Layered Architecture**: Visible separation of concerns (controller -> service -> repository).
- **Security**: JWT-based Authentication and Role-Based Access Control (RBAC).
- **Database**: PostgreSQL with Spring Data JPA and Liquibase migration support.
- **Auditing**: Automatic `createdAt`, `updatedAt`, `createdBy`, and `updatedBy` fields via JPA Auditing.
- **Observability**: 
    - Spring Boot Actuator for health and info.
    - We are still lagging behind on metrics and tracing. So use below tools to add proper observability to app
      - Micrometer Prometheus for metrics.
      - OpenTelemetry support (tracing).
- **API Documentation**: Swagger UI / OpenAPI 3.
- **Global Exception Handling**: Standardized error responses.
- **Standardized API Response**: Consistent JSON wrapper for all endpoints.
- **DevOps**: Dockerfile for containerization.

## Getting Started

### Prerequisites

- JDK 21
- Maven 3.9+

### Running Locally

1. **Clone the repository.**
2. **Setup local database:** Ensure a PostgreSQL instance is running with `microservice_db`. (Created in `pantrypal-postgres` container).
3. **Run the application:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=dev
   ```

### API Documentation

Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Directory Structure

```text
src/main/java/com/nissan/template/
├── bootstrap/       # Main application entry point
├── config/          # Configurations (Security, Audit, etc.)
├── controller/      # REST API controllers
├── domain/          # JPA entities
├── dto/             # Data Transfer Objects
├── exception/       # Global exception handlers and custom exceptions
├── mapper/          # MapStruct mappers
├── repository/      # Spring Data JPA repositories
├── security/        # JWT features, filters, and user details
└── service/         # Business logic services
```

## Security

The application uses JWT for stateless authentication.
- **Login**: `POST /api/v1/auth/login`
- **Register**: `POST /api/v1/auth/register`

Include the token in the `Authorization` header for protected endpoints:
`Authorization: Bearer <your-token>`
