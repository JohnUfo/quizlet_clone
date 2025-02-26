# Quizlet Clone

## 📌 Project Overview
Quizlet Clone is a web-based platform designed to help users create, share, and study flashcards efficiently. It allows users to create **card sets**, request access to shared sets, and manage their study materials securely. This project follows modern backend development practices using **Spring Boot**, **JWT authentication**, and **PostgreSQL** as the database.

---

## ✨ Features
- **User Authentication** (Login/Register using JWT)
- **Create & Manage Flashcard Sets**
- **Request Access to Private Sets**
- **Role-based Authorization**
- **Secure API with Spring Security**
- **Swagger Documentation**
- **Database Persistence with PostgreSQL**

---

## 🛠️ Tech Stack
### **Backend**
- **Spring Boot 3** - Java-based backend framework
- **Spring Security** - Authentication & authorization
- **JWT (JSON Web Token)** - Secure token-based authentication
- **PostgreSQL** - Relational database for storing data
- **Hibernate** - ORM for database management
- **SpringDoc OpenAPI** - API documentation

### **Tools & Dependencies**
- **Maven** - Dependency management
- **Lombok** - Reduces boilerplate code
- **BCrypt** - Password encryption

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

## 📖 API Documentation (Swagger UI)
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

## 🏗️ Project Structure
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
│   ├── application.properties # Configuration file
│── pom.xml                   # Maven dependencies
│── README.md                 # Project documentation
```

---

## 🤝 Contributing
Contributions are welcome! Feel free to fork the repository and submit pull requests.

---

## 📝 Author
Developed by **Umidjontursunov** 🚀

