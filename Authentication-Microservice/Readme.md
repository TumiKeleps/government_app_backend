# 🔒 Authentication Microservice 🌱 Spring Boot Project 📄 Documentation

## 📝 Overview
The application also uses **Consul** for configuration management, enabling dynamic configuration updates and service discovery.
The **Authentication Microservice** is a 🌱 Spring Boot application designed to handle user authentication for a dashboard system. It supports user sign-in, authentication, and provides a secure way to access other services by verifying 🔑 API keys.

The application uses RESTful APIs to facilitate authentication and is built using a modular architecture, including controllers, services, repositories, and application configurations.

### ✨ Key Features
- 🔧 Integrated **Consul** for configuration management and service discovery.
- 🔒 API Key Authentication for secure access to the application.
- 🧩 Modular architecture for controllers, services, and repositories.
- 📊 RESTful APIs for handling user authentication and dashboard user operations.
- 🧪 Unit tests for ensuring functionality.

## 🏗️ Project Structure

### 1. ⚙️ Configuration
- **ApiKeyAuthFilter.java**: Implements 🔑 API key-based authentication to secure endpoints.
- **SecurityConfig.java**: Configures 🔒 Spring Security to apply the API key filter for incoming requests.
- **BadRequest.java**, **InternalServerException.java**, **NotFoundException.java**, **ConflictException.java**, **ForbiddenException.java**: Custom exceptions to handle various ⚠️ error scenarios.
- **ApplicationConfig.java**, **WebMVC.java**: Configurations for the overall application and web settings.

### 2. 🎮 Controllers
- **DashBoardUserController.java**: Handles requests for user operations related to the dashboard, including user sign-in and other user-related actions.

### 3. 🛠️ Services
- **DashBoardUserService.java**: Contains the core 🧠 business logic related to user operations, including sign-in and user management.

### 4. 🗃️ Models
- **DashBoardUsers.java**: Represents the user entity for dashboard users.
- **SignIn.java**: Represents the data required for user sign-in operations.

### 5. 📦 Repository
- **DashBoardUserRepo.java**: 🌱 Spring Data JPA repository interface for managing dashboard user entities in the 🗃️ database.

## ⚙️ Application Configuration
- **Consul**: Used for configuration management and service discovery to enable dynamic updates and easier scaling.
- **application.properties**: Contains configuration settings like 🌐 server port, 🗃️ database configurations, and 🔒 security settings.

## 🔧 Build and Run

### 📋 Prerequisites
- **☕ Java 17** or above
- **🐘 Maven** for dependency management and build

### 🚀 Steps to Run the Application

1. 🛠️ Build the project using 🐘 Maven.
   ```bash
   mvn clean install
   ```
2. ▶️ Run the 🌱 Spring Boot application.
   ```bash
   mvn spring-boot:run
   ```

### ✅ Testing
- **AuthenticationMicroserviceApplicationTests.java**, **DashBoardUserServiceTest.java**, **DashBoardUserControllerTest.java**: Contains 🧪 unit tests for the application. Use the following command to run the tests:
  ```bash
  mvn test
  ```

## 🔒 Authentication
The application employs an **🔑 API Key Authentication** mechanism via the `ApiKeyAuthFilter` class. All requests to protected endpoints must include a valid 🔑 API key in the headers.

## 📖 API Documentation
Here is a summary of the main 🔄 API endpoints available in the application:

### 📊 Dashboard User Endpoints
- **POST /dashboard-user/signin**: User sign-in endpoint.
- **GET /dashboard-user/{id}**: Retrieve details of a specific dashboard user.
- **POST /dashboard-user**: ➕ Add a new dashboard user.
- **DELETE /dashboard-user/{id}**: 🗑️ Delete a specific user from the dashboard.

## ⚠️ Error Handling
The application utilizes custom exceptions like `BadRequest`, `InternalServerException`, `NotFoundException`, `ConflictException`, and `ForbiddenException` to handle different error types gracefully. Each exception is mapped to an appropriate HTTP 🚦 status code.


## 🏁 Conclusion
The **Authentication Microservice** provides a simple yet effective solution for handling user authentication and securing access to the dashboard. Built using 🌱 Spring Boot, its modular architecture makes it easy to extend and maintain, and the use of custom security filters ensures a secure authentication process.

