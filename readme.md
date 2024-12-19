# RESTful Java API | **Product Management |** Spring Boot

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/spring--boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white) ![Maven](https://img.shields.io/badge/ApacheMaven-C71A36?logo=apachemaven&logoColor=white&style=for-the-badge) ![PostgreSQL](https://img.shields.io/badge/postgresql-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white) ![Hibernate](https://img.shields.io/badge/Hibernate-59666C.svg?style=for-the-badge&logo=hibernate&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) ![JUnit](https://img.shields.io/badge/JUnit-25A162.svg?style=for-the-badge&logo=JUnit&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

## Description

This project is a **RESTful API** built with **Spring Boot**, designed for product management, offering full **CRUD** (Create, Read, Update, Delete) functionalities and other useful features. It was developed through self-learning by combining YouTube tutorials, documentation, articles, and consulting AI for quick questions and repetitive task optimization.

### Key Features:

- **Data Persistence**:
    - The API uses **Spring Data JPA** for database access abstraction, enabling the use of **JpaRepository** for predefined CRUD operations and queries.
    - The product entity is modeled with annotations such as `@Entity` and `@Table`, and the primary key is auto-generated with `@GeneratedValue(strategy = GenerationType.AUTO)`.
    - Unique global IDs are generated using **UUID.**
    - Configured to use a PostgreSQL database.
- **Input Validation**:
    - Validation is handled using **Bean Validation** annotations (e.g., `@NotNull`, `@Size`, etc.), ensuring the integrity of received data.
    - A custom **GlobalExceptionHandler** centralizes error and exception handling.
- **Resource Navigation**:
    - Implements hypermedia support with **Spring HATEOAS**, adding navigational links to API resources.
- **Authentication and Security**:
    - **Spring Security** manages authentication and authorization, using **JWT (JSON Web Tokens)** for stateless endpoint protection.
    - Permissions are defined in a dedicated class (`SecurityConfiguration`) with roles (`ROLE_ADMIN` and `ROLE_USER`) and token validation through a dedicated filter (`SecurityFilter`).
    - Password hashing is implemented with **BCrypt** for secure credential storage.
    - Only users with the **ADMIN** role can perform PUT/POST/DELETE operations.
- **Documentation**:
    - **Springdoc-OpenAPI**: Automatically generates OpenAPI 3.0 documentation, integrated with **Swagger UI** for interactive visualization and testing in the browser.
- **Deployment:**
    
    The application is deployed on **Render**, a platform providing free web app hosting. Configured for the server and PostgreSQL database with environment variables.

    - Deployed on **Render**, with PostgreSQL database and proper environment variables.
    - **Docker Container** configured for portability and efficient execution.
    
    **URLs:**
    
    - **API Base URL:** https://product-api-spring-boot.onrender.com
    - **Swagger UI:** https://product-api-spring-boot.onrender.com/swagger-ui/index.html

### **Project Architecture:**

The project follows a **Layered Architecture**, adhering to best practices for modularity and separation of concerns:

- **Controller/Resource Layer**: Manages HTTP requests and sends responses to clients.
- **Service Layer**: Centralizes business logic for better reusability and cohesion.
- **Repository Layer**: Handles database access using the DAO pattern with Spring Data JPA.
- **Model and DTO Layer**: Organizes data, separating domain logic from transfer objects.
- **Security Layer**: Implements authentication and access control using Spring Security.
- **Exception Layer**: Centrally manages errors, providing standardized and clear responses.

### **Installation and Local Execution**

1. **Clone the repository:**
    
    ```bash
    git clone <repo-url>
    ```
    
2. **Set up environment variables:**
    1. {DATASOURCE_URL}
    2. {DATASOURCE_USERNAME}
    3. {DATASOURCE_PASSWORD}
3. **Run the project:**
    
    ```bash
    ./mvnw spring-boot:run
    ```
    
4. **Run with Docker:**
    - Build the Docker image:
        
        ```bash
        docker build -t product-api .
        ```
        
    - Run the container:
        
        ```bash
        docker run -p 8080:8080 product-api
        ```

