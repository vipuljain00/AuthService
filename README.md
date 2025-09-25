# AuthService

AuthService is a Spring Boot application that provides authentication and authorization functionality using JWT (JSON Web Tokens). It is designed as a RESTful API service, making it easy to integrate with other applications that require secure user authentication.

## Key Features

- **Spring Boot & Spring Security**: Utilizes Spring Boot for rapid development and Spring Security for robust authentication and authorization.
- **JWT Authentication**: Issues and validates JWT tokens for stateless, secure authentication.
- **User and Role Management**: Supports user management with roles stored in a relational database.
- **MySQL Database**: Uses MySQL for persistent storage of user and token data.
- **JPA & Hibernate ORM**: Implements data persistence with JPA and Hibernate for seamless ORM.
- **Refresh Token Support**: Implements refresh tokens for secure, long-lived sessions.
- **RESTful API Endpoints**: Provides endpoints for authentication, user registration, and token management.
- **Input Validation**: Includes utility methods for validating usernames and emails.

## Main Components

- **Entities**
  - `UserInfo`: Represents a user in the system, including username, password, and roles.
  - `UserRole`: Defines user roles for authorization.
  - `RefreshToken`: Stores refresh tokens associated with users.

- **Repositories**
  - `UserInfoRepo`: CRUD operations for user data.
  - `RefreshTokenRepo`: Manages refresh tokens.

- **Services**
  - `MyUserDetailsService`: Loads user details and handles user sign-up logic.
  - `JwtService`: Handles JWT creation, validation, and extraction.
  - `MyUserDetails`: Implements Spring Security's `UserDetails` for custom user info.

- **Security**
  - `SecurityConfig`: Configures authentication providers, password encoding, and security filters.
  - `JwtAuthenticationFilter`: Intercepts requests to validate JWT tokens.

- **DTOs**
  - `UserInfoDto`, `AuthRequestDTO`, `JWTResponseDto`: Used for transferring data between client and server.

- **Utility**
  - `ValidateUtil`: Provides input validation for usernames and emails.

## Getting Started

1. **Clone the repository**  
   `git clone https://github.com/vipuljain00/AuthService.git`

2. **Configure MySQL Database**  
   Update your database connection properties in `application.properties`.

3. **Build and Run**  
   Build using Gradle/Maven and run the application:
   ```
   ./gradlew bootRun
   ```
   or
   ```
   mvn spring-boot:run
   ```

4. **API Usage**  
   - Register a new user
   - Authenticate to receive JWT and refresh token
   - Use the JWT to access protected endpoints

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- JPA/Hibernate
- MySQL
- Lombok

## License

This project is currently unlicensed.

---
For any queries or contributions, please visit the [GitHub repository](https://github.com/vipuljain00/AuthService).
