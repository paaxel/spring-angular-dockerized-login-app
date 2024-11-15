# Login Application Guide

The Login Application is a comprehensive starter project that demonstrates how to set up a full-stack application with JWT authentication.  
Built using Angular for the frontend, Spring Boot for the backend, and PostgreSQL for data storage, this example is particularly valuable for those learning full-stack development with Spring and Angular.  
The application is also containerized to help developers understand how to containerize Spring and Angular applications with a PostgreSQL database managed in a Docker image.

## Prerequisites

Before starting, ensure you have the following tools installed:

- IntelliJ IDEA (recommended IDE)
- JDK 17 (Eclipse Temurin recommended)
- Visual Studio (Windows only, required for library compilation)
- Docker
- Node.js (for local development)

## Installation Options

### 1. Docker Installation (Recommended)

The easiest way to get started is using Docker Compose.

1. Build the backend JAR `/web/target/*.jar` (is suggested to use an IDE)

2. Configure environment variables (optional):
   ```
   DATASOURCE_PASSWORD - Database password
   SECURITY_JWT_SECRET - JWT token signing secret
   API_URL - Backend base URL
   ```

3. Start the application:
   ```bash
   docker-compose up
   ```

4. Access the application:
   - Frontend: `http://localhost:8081`
   - Backend: `http://localhost:8080`

### 2. Standard Installation (Local Development)

#### Database Setup
1. Navigate to `login-app-db-initializer/sql`
2. Execute the provided SQL scripts to initialize the database

#### Frontend Setup
1. Navigate to `login-app-fe/src`
2. Run compilation script:
   ```bash
   compile.bat
   ```
3. Start the development server:
   ```bash
   start.bat
   ```
4. Access the frontend at `http://localhost:3500`

#### Backend Setup
1. Open `docker-app-backend` project in your IDE
2. Configure the following application parameters:
   ```properties
   security.jwt.secret=<your-secure-random-string>
   persistence.datasource.url=jdbc:postgresql://127.0.0.1:5432/postgres
   persistence.datasource.username=postgres
   persistence.datasource.password=<your-database-password>
   ```
3. Run `ProvaLoginStarter` class
4. Verify the backend is running:
   - `http://localhost:8080`
   - `http://localhost:8080/version`

## API Documentation

The API documentation is organized in the `api-documentation` directory:

```
api-documentation/
├── rest/         # REST API documentation
└── soap/         # SOAP API documentation
```

## Contributing

We welcome contributions! Here's how you can help:

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

Please ensure your code follows the project's coding standards and includes appropriate tests.

## Issues and Bug Reports

Use the GitHub issue tracker to report bugs or suggest new features. When reporting issues, please include:

- Detailed description of the problem
- Steps to reproduce
- Expected vs actual behavior
- Environment details

## License

This project is licensed under the GNU General Public License v3.0 (GPLv3). See [LICENSE](LICENSE) for more details.

## Technology Stack

- Backend: Spring Boot
- Frontend: Angular
- Database: PostgreSQL
- Containerization: Docker
- Authentication: JWT

## Contact

- **Maintainer**: Alessandro Pagliaro
- **Email**: alessandro.a.pagliaro@gmail.com
- **GitHub**: [Profile](https://github.com/paaxel)

## Acknowledgments

We acknowledge and appreciate the following third-party libraries and resources used in this project:

- Spring Framework
- Angular
- PostgreSQL
- Docker