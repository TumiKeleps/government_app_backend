# 📊 Performance Indicator 🌱 Spring Boot Project 📄 Documentation

## 📝 Overview
The **Performance Indicator** project is a 🌱 Spring Boot application designed to track and manage 📈 performance indicators for different 🏢 sectors, 🌍 provinces, and 📅 quarters. It uses 🔄 RESTful APIs to interact with performance data, allowing 👥 users to access and update information related to performance indicators, progress ratings, and other related entities. The application also uses **Consul** for configuration management, enabling dynamic configuration updates and service discovery.

The application is built using the 🌱 Spring Boot framework and employs a 🧩 modular architecture consisting of controllers, services, repositories, DTOs, and application configurations.

### ✨ Key Features
- 🔧 Integrated **Consul** for configuration management and service discovery.
- 🔒 Authentication with custom security filters for 🔑 API key verification.
- 🔄 RESTful APIs to fetch and update 📊 performance indicators.
- 🧩 Modular architecture using controllers, services, and repositories.
- 📌 Support for different enums like `SectorEnum`, `QuarterEnum`, and `ProvinceEnum`.

## 🏗️ Project Structure
### 1. ⚙️ Configuration
- **ApiKeyAuthFilter.java**: Implements 🔑 API key-based authentication for securing the endpoints.
- **SecurityConfig.java**: Configures 🔒 Spring Security to include the API key filter for incoming requests.
- **BadRequest.java**, **InternalServerException.java**, **NotFoundException.java**, **PreconditionRequest.java**: Custom exceptions for handling different ⚠️ error scenarios in the application.

### 2. 🎮 Controllers
- **PerfomanceIndicatorController.java**: Handles endpoints related to 📊 performance indicators, including fetching, updating, and deleting performance data.
- **ProgressController.java**, **QuarterController.java**, **SectorController.java**: Additional controllers to manage data related to 📈 progress, 📅 quarters, and 🏢 sectors respectively.

### 3. 🛠️ Services
- **PerfomanceIndicatorService.java**: Contains the core 🧠 business logic for managing 📊 performance indicators. This includes fetching the data based on different filters and managing performance-related requests.

### 4. 🗃️ Models
- **PerfomanceIndicatorModel.java**: Represents the core entity for 📊 performance indicators.
- **SectorEnum.java**, **QuarterEnum.java**, **ProvinceEnum.java**, **ProgressRatingEnum.java**: Enum classes representing different categorical data used in the application.
- **ActualPerfomance.java**: Represents the actual 📈 performance data of an entity.

### 5. 📦 Data Transfer Objects (DTOs)
- **PerformanceRequest.java**, **SearchRequest.java**, **SearchRequestSector.java**, **SearchRequestSectorProvince.java**, **SearchRequestSectorProvinceOnly.java**: DTOs for encapsulating request data for different 🔄 API endpoints.

### 6. 🗄️ Repository
- **PerfomanceIndicatorRepo.java**: 🌱 Spring Data JPA repository interface for interacting with the underlying 🗃️ database to manage 📊 performance indicator entities.

## ⚙️ Application Configuration
- **application.properties**: Contains configuration settings such as 🌐 server port, 🗃️ database configurations, and 🔒 security settings.

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
- **PerfomanceIndicatorApplicationTests.java**: Contains 🧪 unit tests for the application. Use the following command to run the tests:
  ```bash
  mvn test
  ```

## 🔒 Authentication
The application uses an **🔑 API Key Authentication** mechanism implemented in the `ApiKeyAuthFilter` class. Requests to protected endpoints must include a valid 🔑 API key in the headers.

## 📖 API Documentation
Here is a summary of the key 🔄 API endpoints available in the application:

### 📊 Performance Indicator Endpoints
- **GET /performance-indicators**: Retrieve a list of 📊 performance indicators with optional filters for 🏢 sector, 🌍 province, and 📅 quarter.
- **POST /performance-indicators**: ➕ Add a new 📊 performance indicator.
- **PATCH /performance-indicators/{id}**: 🔄 Update an existing 📊 performance indicator.
- **DELETE /performance-indicators/{id}**: 🗑️ Delete a specific 📊 performance indicator.

### 📈 Progress Endpoints
- **GET /progress**: Retrieve 📈 progress data.

## ⚠️ Error Handling
The application uses custom exception classes like `BadRequest`, `InternalServerException`, and `NotFoundException` to handle different types of errors gracefully. Each exception is mapped to an appropriate HTTP 🚦 status code.


## 🏁 Conclusion
The 📊 Performance Indicator project provides a robust platform for managing performance data efficiently. Its 🧩 modular design makes it easy to extend and adapt to new requirements. The use of 🌱 Spring Boot provides scalability and ease of development, ensuring high 📈 performance and reliability.

