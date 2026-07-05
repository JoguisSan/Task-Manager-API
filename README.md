# 📋 Task Manager API

RESTful API for task management built with **Spring Boot 3** and **Java 17**, featuring **JWT** authentication, automated tests, and a CI/CD pipeline. Built as a portfolio project to demonstrate backend development best practices.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ Features

- 🔐 User authentication and registration with **JWT**
- 🔒 Passwords encrypted with **BCrypt**
- ✅ Full task CRUD (create, list, retrieve, update, delete)
- 👤 Each user can only access their own tasks
- 🔎 Task filtering by status (`PENDING`, `IN_PROGRESS`, `DONE`) and pagination
- ⚠️ Global error handling with standardized responses
- 📖 Interactive API documentation via **Swagger/OpenAPI**
- 🧪 Unit and integration tests (JUnit 5, Mockito, MockMvc)
- 🐳 Containerized with **Docker** and **Docker Compose** (app + PostgreSQL)
- ⚙️ **CI** pipeline with GitHub Actions (build + tests on every push/PR)

## 🛠️ Tech Stack

| Layer            | Technology                           |
|------------------|----------------------------------------|
| Language         | Java 17                              |
| Framework        | Spring Boot 3 (Web, Security, JPA)   |
| Database         | PostgreSQL (production) / H2 (tests) |
| Authentication   | JWT (jjwt)                           |
| Documentation    | springdoc-openapi (Swagger UI)       |
| Testing          | JUnit 5, Mockito, Spring Security Test |
| Build            | Maven                                |
| Containerization | Docker, Docker Compose               |
| CI/CD            | GitHub Actions                       |

## 🏗️ Architecture

The project follows a layered architecture:

```
controller  → handles HTTP requests and returns responses
service     → contains business logic
repository  → data access via Spring Data JPA
entity      → domain models (JPA)
dto         → data transfer objects (request/response)
security    → JWT filter and authentication configuration
exception   → centralized error handling
config      → configuration (Security, OpenAPI)
```

## 🚀 Getting Started

### Option 1 — Docker Compose (recommended)

Spins up the application and the PostgreSQL database with a single command:

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`.

### Option 2 — Locally with Maven

Prerequisites: Java 17, Maven, PostgreSQL running locally.

```bash
# Set the environment variables (or adjust application.yml)
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=taskmanager
export DB_USER=postgres
export DB_PASSWORD=postgres

mvn spring-boot:run
```

### Running the tests

```bash
mvn test
```

## 📖 API Documentation (Swagger)

With the application running, visit:

```
http://localhost:8080/swagger-ui.html
```

## 🔑 Authentication — quick flow

**1. Register a user**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@email.com","password":"password123"}'
```

**2. Log in and get the token**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"password123"}'
```

**3. Use the token on protected requests**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <YOUR_TOKEN_HERE>"
```

## 📌 Main Endpoints

| Method | Endpoint             | Description                                              | Auth Required |
|--------|-----------------------|-----------------------------------------------------------|---------------|
| POST   | `/api/auth/register`  | Creates a new user                                         | No            |
| POST   | `/api/auth/login`     | Authenticates and returns the JWT token                    | No            |
| POST   | `/api/tasks`          | Creates a new task                                         | Yes           |
| GET    | `/api/tasks`          | Lists the user's tasks (paginated, filterable by status)   | Yes           |
| GET    | `/api/tasks/{id}`     | Retrieves a specific task                                   | Yes           |
| PUT    | `/api/tasks/{id}`     | Updates a task                                              | Yes           |
| DELETE | `/api/tasks/{id}`     | Deletes a task                                              | Yes           |

## 🗺️ Possible Future Improvements

- Refresh tokens
- Task sharing between users
- Due date notifications
- Automated deployment (CD) to a cloud service

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

---

Built as a portfolio project, with a focus on architecture, security, and testing best practices in Spring Boot applications.
