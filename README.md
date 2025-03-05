# Quizlet Clone

## 📌 Project Overview
Quizlet Clone is a full-stack web application inspired by Quizlet, designed to help users create, manage, and study flashcards. It allows users to create **card sets**, request access to shared sets, and manage their study materials securely. This project follows modern backend development practices using **Spring Boot**, **JWT authentication**, and **PostgreSQL** as the database.

---

## ✨ Features
- **User Authentication**: Secure login and registration using JWT (JSON Web Tokens).
- **Card Sets**: Create, update, and delete card sets with customizable languages and privacy settings.
- **Flashcards**: Add, edit, and study flashcards within a card set.
- **Access Control**: Request access to private card sets and manage pending requests.
- **Responsive UI**: Clean and intuitive user interface with interactive card flipping.
- **Swagger API Documentation**: Integrated OpenAPI documentation for backend APIs.

---

## 🛠️ Tech Stack
### **Backend**
- **Spring Boot 3**: Core framework for building the RESTful API.
- **Spring Security**: Handles authentication and authorization.
- **JWT (JSON Web Token)**: Secure token-based authentication.
- **PostgreSQL**: Relational database for storing application data.
- **Hibernate**: ORM for database management.
- **SpringDoc OpenAPI**: Generates API documentation.

### **Frontend**
- **HTML/CSS**: For structuring and styling the application.
- **JavaScript**: For dynamic interactions and API calls.
- **Thymeleaf**: Server-side templating engine for rendering views.

### **Tools & Dependencies**
- **Maven**: Build and dependency management.
- **Lombok**: Reduces boilerplate code.
- **BCrypt**: Password encryption.
- **Docker**: Containerization for easy deployment.
- **Postman**: API testing and debugging.

---

## 🚀 Installation & Setup
### **Prerequisites**
Make sure you have the following installed on your system:
- **Java 21+**
- **Maven**
- **PostgreSQL** (Ensure the database is running)
- **Git**

### **Step 1: Clone the Repository**
```bash
git clone https://github.com/JohnUfo/quizlet-clone.git
cd quizlet-clone
```

### **Step 2: Configure Database**
Update your `application.properties` file with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/quizlet_clone
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### **Step 3: Build and Run**
Run the application using:
```bash
mvn spring-boot:run
```
The server should start at **http://localhost:8080**.

---

## 📚 API Documentation (Swagger UI)
After running the project, access API documentation at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🔐 Authentication & Authorization
The project uses **JWT-based authentication**. Here’s how it works:
- Users **register** with a username and password.
- Upon login, they receive a **JWT token**.
- The token is sent with every request for authentication.
- Access to different endpoints is controlled via **Spring Security**.

### **Example Authentication Request (Login)**
#### **POST `/login`**
```json
{
  "username": "testuser",
  "password": "password123"
}
```
_Response:_
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI..."
}
```

Use this token in the `Authorization` header for secure requests:
```bash
curl -H "Authorization: Bearer YOUR_TOKEN" http://localhost:8080/protected-endpoint
```

---

## 🏰️ Project Structure
```
quizlet-clone/
│── src/
│   ├── main/java/online/muydinov/quizletclone/
│   │   ├── config/          # Security configurations
│   │   ├── controller/      # API controllers
│   │   ├── entity/          # Database entities/models
│   │   ├── repository/      # Database repositories
│   │   ├── service/         # Business logic services
│── resources/
│   ├── static/              # Static assets (CSS, JS, images)
│   ├── templates/           # Thymeleaf templates (HTML)
│   ├── application.properties # Configuration file
│── pom.xml                   # Maven dependencies
│── README.md                 # Project documentation
```

---

## 🐓 Docker Deployment
1. **Build and run the application using Docker**:
   ```bash
   docker-compose up --build
   ```

2. **Access the application**:
   - Open your browser and navigate to `http://localhost:8080`.

---
