# Tourism Backend

This project is a backend service for a tourism application. The service is built using Spring Boot and provides RESTful APIs for managing users and their interactions with the application.

## Table of Contents

- [Tourism Backend](#tourism-backend)
  - [Table of Contents](#table-of-contents)
  - [Features](#features)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
  - [Usage](#usage)
  - [Project Structure](#project-structure)
  - [Contributing](#contributing)
  - [License](#license)

## Features

- User registration and authentication
- CRUD operations for users
- Docker support for containerization
- Integration with PostgreSQL database

## Getting Started

### Prerequisites

- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/tourism-backend.git
    cd tourism-backend
    ```

2. Set up the PostgreSQL database and update the `application.properties` file with your database credentials.

3. Build the project:

    ```bash
    ./mvnw clean install
    ```

4. Run the application:

    ```bash
    ./mvnw spring-boot:run
    ```

5. Alternatively, you can run the application using Docker:

    ```bash
    docker-compose up --build
    ```

## Usage

Once the application is running, you can access the API documentation at `http://localhost:8080/swagger-ui.html`.

## Project Structure

```plaintext
tourism-backend/
├── .mvn/                         # Maven wrapper files
├── src/                          # Source files
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tourism/
│   │   │           └── backend/
│   │   │               ├── controller/  # REST controllers
│   │   │               ├── model/       # Entity models
│   │   │               ├── repository/  # Data repositories
│   │   │               ├── service/     # Service layer
│   │   │               └── BackendApplication.java  # Main application
│   │   └── resources/
│   │       ├── application.properties  # Configuration file
│   │       └── ...
│   └── test/
│       └── java/
│           └── com/
│               └── tourism/
│                   └── backend/
│                       └── BackendApplicationTests.java  # Test class
├── Dockerfile                     # Dockerfile for the application
├── docker-compose.yml             # Docker Compose configuration
├── mvnw                           # Maven wrapper script (Unix)
├── mvnw.cmd                       # Maven wrapper script (Windows)
├── pom.xml                        # Maven configuration file
└── README.md                      # This README file
```
## Contributing

We welcome contributions to this project. If you have an idea for an enhancement or have found a bug, please follow these steps:

1. **Fork the Repository**: Click on the 'Fork' button at the top right corner of this repository's page. This creates your own copy of the repository.
2. **Clone the Repository**: Clone your forked repository to your local machine using:
    ```bash
    git clone https://github.com/your-username/tourism-backend.git
    cd tourism-backend
    ```
3. **Create a Branch**: Create a new branch for your feature or bug fix.
    ```bash
    git checkout -b feature-name
    ```
4. **Make Changes**: Make your changes to the codebase.
5. **Commit Changes**: Commit your changes with a descriptive commit message.
    ```bash
    git commit -m "Description of the feature or fix"
    ```
6. **Push Changes**: Push your changes to your forked repository.
    ```bash
    git push origin feature-name
    ```
7. **Create a Pull Request**: Go to the original repository and click on 'Pull Requests'. Click on 'New Pull Request' and select your branch. Submit the pull request for review.

Please ensure your code adheres to the project's coding standards and includes tests for any new functionality.

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.
