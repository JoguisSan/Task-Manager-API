# 📋 Task Manager API

API RESTful de gerenciamento de tarefas construída com **Spring Boot 3** e **Java 17**, com autenticação **JWT**, testes automatizados e pipeline de CI/CD. Projeto desenvolvido como peça de portfólio para demonstrar boas práticas de desenvolvimento backend.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ Funcionalidades

- 🔐 Autenticação e registro de usuários com **JWT**
- 🔒 Senhas criptografadas com **BCrypt**
- ✅ CRUD completo de tarefas (criar, listar, buscar, atualizar, excluir)
- 👤 Cada usuário só acessa suas próprias tarefas
- 🔎 Filtro de tarefas por status (`PENDING`, `IN_PROGRESS`, `DONE`) e paginação
- ⚠️ Tratamento global de erros com mensagens padronizadas
- 📖 Documentação interativa via **Swagger/OpenAPI**
- 🧪 Testes unitários e de integração (JUnit 5, Mockito, MockMvc)
- 🐳 Containerização com **Docker** e **Docker Compose** (app + PostgreSQL)
- ⚙️ Pipeline de **CI** com GitHub Actions (build + testes a cada push/PR)

## 🛠️ Stack Técnica

| Camada           | Tecnologia                          |
|------------------|--------------------------------------|
| Linguagem        | Java 17                              |
| Framework        | Spring Boot 3 (Web, Security, JPA)   |
| Banco de dados   | PostgreSQL (produção) / H2 (testes)  |
| Autenticação     | JWT (jjwt)                           |
| Documentação     | springdoc-openapi (Swagger UI)       |
| Testes           | JUnit 5, Mockito, Spring Security Test |
| Build            | Maven                                |
| Containerização  | Docker, Docker Compose               |
| CI/CD            | GitHub Actions                       |

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas (*layered architecture*):

```
controller  → recebe requisições HTTP e retorna respostas
service     → contém as regras de negócio
repository  → acesso a dados via Spring Data JPA
entity      → modelos de domínio (JPA)
dto         → objetos de transferência de dados (request/response)
security    → filtro JWT e configuração de autenticação
exception   → tratamento centralizado de erros
config      → configurações (Security, OpenAPI)
```

## 🚀 Como executar

### Opção 1 — Docker Compose (recomendado)

Sobe a aplicação e o banco PostgreSQL com um único comando:

```bash
docker compose up --build
```

A API estará disponível em `http://localhost:8080`.

### Opção 2 — Localmente com Maven

Pré-requisitos: Java 17, Maven, PostgreSQL rodando localmente.

```bash
# Configure as variáveis de ambiente (ou ajuste application.yml)
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=taskmanager
export DB_USER=postgres
export DB_PASSWORD=postgres

mvn spring-boot:run
```

### Rodando os testes

```bash
mvn test
```

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui.html
```

## 🔑 Autenticação — fluxo rápido

**1. Registrar um usuário**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"joao","email":"joao@email.com","password":"senha123"}'
```

**2. Fazer login e obter o token**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"joao","password":"senha123"}'
```

**3. Usar o token nas requisições protegidas**
```bash
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <SEU_TOKEN_AQUI>"
```

## 📌 Endpoints principais

| Método | Endpoint            | Descrição                          | Autenticado |
|--------|---------------------|-------------------------------------|-------------|
| POST   | `/api/auth/register`| Cria um novo usuário                | Não         |
| POST   | `/api/auth/login`   | Autentica e retorna o token JWT      | Não         |
| POST   | `/api/tasks`        | Cria uma nova tarefa                | Sim         |
| GET    | `/api/tasks`        | Lista as tarefas do usuário (paginado, filtro por status) | Sim |
| GET    | `/api/tasks/{id}`   | Busca uma tarefa específica          | Sim         |
| PUT    | `/api/tasks/{id}`   | Atualiza uma tarefa                  | Sim         |
| DELETE | `/api/tasks/{id}`   | Remove uma tarefa                    | Sim         |

## 🗺️ Possíveis melhorias futuras

- Refresh tokens
- Compartilhamento de tarefas entre usuários
- Notificações de prazos próximos
- Deploy automatizado (CD) para um serviço cloud

## 📄 Licença

Este projeto está sob a licença MIT — veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

Desenvolvido como projeto de portfólio, com foco em boas práticas de arquitetura, segurança e testes em aplicações Spring Boot.
