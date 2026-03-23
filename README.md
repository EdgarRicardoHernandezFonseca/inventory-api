# Inventory API

A RESTful API for inventory management built with Spring Boot. This project provides product management, stock control, and secure authentication using JWT.

---

## 🚀 Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA
* Spring Security
* JWT (JSON Web Tokens)
* PostgreSQL
* H2 (for testing)
* Maven
* Lombok
* Swagger / OpenAPI

---

## 📦 Features

* Product management (create and retrieve products)
* Advanced product search:

  * By name
  * By price range
  * By low stock
* Inventory control:

  * Increase stock
  * Decrease stock
  * Track stock movements
* JWT-based authentication and authorization
* Standardized API responses
* Input validation
* Unit and controller testing

---

## 🔐 Authentication

This API uses JWT to secure endpoints.

### Register

POST `/auth/register`

```json
{
  "username": "edgar",
  "password": "1234"
}
```

### Login

POST `/auth/login`

```json
{
  "username": "edgar",
  "password": "1234"
}
```

Response:

```
jwt-token
```

---

## 📚 Main Endpoints

### Products

* Create product
  POST `/products`

* Get all products (paginated)
  GET `/products?page=0&size=10`

* Get low stock products
  GET `/products/low-stock`

* Search products
  GET `/products/search`

Optional query params:

* `name`
* `minPrice`
* `maxPrice`
* `lowStock`
* `page`
* `size`

---

### Stock

* Increase stock
  POST `/stock/increase/{productId}?quantity=10`

* Decrease stock
  POST `/stock/decrease/{productId}?quantity=5`

---

## 🗄️ Data Model (Summary)

### Product

* id
* name
* price
* stock
* minStock

### StockMovement

* id
* product
* type (INCREASE / DECREASE)
* quantity
* reason
* createdAt

### User

* id
* username
* password
* role

---

## ⚙️ Configuration

Configure the following in `application.properties` or `application.yml`:

* Database connection (PostgreSQL)
* JWT settings (secret key, expiration time)

---

## ▶️ Run the Project

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🧪 Testing

Includes:

* Unit tests (services)
* Controller tests (MockMvc)

Run tests:

```bash
mvn test
```

---

## 📖 API Documentation

Swagger UI available at:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧠 Technical Decisions

* Layered architecture (Controller → Service → Repository)
* DTO pattern to decouple API from entities
* Mapper for data transformation
* Centralized API response structure
* Custom exception handling
* Pagination for scalability

---

## 📌 Future Improvements

* Role-based access control (RBAC)
* Full audit logging
* Redis caching
* Docker support
* CI/CD pipeline
* Improved test coverage
* Rate limiting

---

## 👨‍💻 Author

Edgar Ricardo Hernández Fonseca

Backend Developer

Java | Spring Boot | REST APIs | AWS

---

# 💼 Freelance Services

I can help you build or customize:

REST APIs with Java and Spring Boot

Authentication systems (JWT / OAuth2)

Backend architecture for scalable applications

Database design and integration

Microservices and cloud-ready backend systems

Feel free to contact me if you need a backend developer for your project.

---

## 📄 License

This project is for educational and portfolio purposes.

🐳 Docker Setup

This project includes Docker support to simplify local development and deployment using containers.

📦 Prerequisites

Make sure you have installed:

Docker
Docker Compose
🚀 Run the application with Docker
Clone the repository:
git clone https://github.com/your-username/inventory-api.git
cd inventory-api
Build and start the containers:
docker-compose up --build
The application will be available at:
http://localhost:8080
🗄️ Services Included
inventory-api → Spring Boot application
postgres → PostgreSQL database
⚙️ Environment Configuration

The application uses a Docker-specific configuration:

spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/inventory_db
    username: postgres
    password: postgres

Note: The hostname postgres is defined in docker-compose.yml.

🧪 Running Tests

Tests are executed using the H2 in-memory database:

mvn clean test
🧹 Stop Containers
docker-compose down

To remove volumes as well:

docker-compose down -v
📌 Notes
The database data is persisted using Docker volumes.
You can modify environment variables directly in docker-compose.yml.
Swagger UI is available at:
http://localhost:8080/swagger-ui.html


📦 Deployment Ready

This project is production-ready and can be deployed using:

Docker / Docker Compose
Cloud platforms (AWS, Render, Railway, etc.)
CI/CD pipelines (GitHub Actions, GitLab CI)