# Fleet Management Service 🚀

This project is a Java-based Spring Boot application designed for efficient fleet management, focusing on real-time vehicle status tracking and historical data logging.

## Badges 🛡️

- [![Java Version](https://img.shields.io/badge/java-%3E%3D17-blue)](https://www.java.com/)
- [![Spring Boot](https://img.shields.io/badge/spring%20boot-3.x-brightgreen)](https://spring.io/projects/spring-boot)
- [![Maven](https://img.shields.io/badge/maven-3.x-orange)](https://maven.apache.org/)
- [![Redis](https://img.shields.io/badge/redis-6.x-red)](https://redis.io/)
- [![JPA/Hibernate](https://img.shields.io/badge/jpa%2Fhibernate-6.x-informational)](https://hibernate.org/)

## Table of Contents 📜

- [Project Title & Badges](#fleet-management-service-%F0%9F%92%A3)
- [Description](#description)
- [Table of Contents](#table-of-contents-%F0%9F%97%92%EF%B8%8F)
- [Features](#features-%E2%9C%A8)
- [Tech Stack](#tech-stack-%E2%9A%99%EF%B8%8F)
- [Installation](#installation-%E2%9A%9B%EF%B8%8F)
- [Usage](#usage-%E2%9C%89%EF%B8%8F)
- [API Reference](#api-reference-%F0%9F%97%BE%EF%B8%8F)
- [Project Structure](#project-structure-%F0%9F%93%81)
- [Contributing](#contributing-%F0%9F%94%97)
- [License](#license-%F0%9F%93%9C)
- [Important links](#important-links-%F0%9F%94%97)
- [Footer](#footer-%F0%9F%97%BA%EF%B8%8F)

## Description 📝

The Fleet Management Service is a robust backend system developed in Java using the Spring Boot framework. It is engineered to manage and track the real-time status and historical data of a fleet of vehicles. The system leverages Redis for caching and asynchronous messaging (Pub/Sub) to efficiently handle high volumes of status updates. It utilizes JPA and Hibernate for data persistence, storing current vehicle status and historical records in a relational database.

## Features ✨

- **Real-time Vehicle Status Updates:** Receives and processes vehicle status updates, including location, battery percentage, and speed.
- **Status Publishing:** Publishes incoming vehicle status updates to a Redis channel for real-time dissemination.
- **Status Consumption:** Subscribes to Redis channels to consume and process vehicle status updates.
- **Historical Data Logging:** Stores historical vehicle status data for analysis and auditing.
- **Efficient Data Upsert:** Implements an `upsertIfNewer` strategy for the current vehicle status table, ensuring only newer records overwrite older ones based on the event timestamp.
- **Caching:** Utilizes Redis for caching `VehicleStatus` and `VehicleStatusHistory` to improve retrieval performance.
- **Data Validation:** Employs Jakarta Bean Validation for incoming DTOs to ensure data integrity.

## Tech Stack 🛠️

- **Languages:** Java 17
- **Frameworks:** Spring Boot 3.x, Spring Data Redis, Spring Cache
- **Databases:** PostgreSQL (assumed, due to JPA/Hibernate and `ON CONFLICT` syntax common in PostgreSQL)
- **Messaging:** Redis Pub/Sub
- **Caching:** Redis
- **Build Tool:** Gradle
- **Serialization:** Jackson (JSON)
- **Other:** Lombok, Jakarta EE specifications (JPA, Bean Validation)

## Installation ⚙️

This project is built using Gradle. Ensure you have Java 17+ and Gradle installed.

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/shanooni/fleet-management-service.git
    cd fleet-management-service
    ```

2.  **Build the project:**
    ```bash
    ./gradlew build
    ```

3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```

**Dependencies:**
- **Java Development Kit (JDK):** Version 17 or higher.
- **Gradle:** The build automation tool.
- **Redis Server:** Required for caching and message brokering. Ensure a Redis instance is running and accessible.
- **Database:** A relational database (e.g., PostgreSQL) configured via `application.properties` is required for JPA persistence. Ensure the necessary JDBC driver is available.

**Configuration:**
Update `src/main/resources/application.properties` with your specific Redis and database connection details.

```properties
# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Database Configuration (Example for PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/fleetdb
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## Usage 📍

The Fleet Management Service exposes a RESTful API to manage vehicle status. Vehicles can send their status updates, which are then processed, cached, and logged.

### Sending Vehicle Status Updates 🚗💨

To update a vehicle's status, send a `POST` request to the `/api/v1/vehicles/{vehicleId}/status` endpoint.

**Request Body (JSON):**

```json
{
  "longitude": -74.0060,
  "latitude": 40.7128,
  "batteryPercentage": 85.5,
  "speed": 65.0,
  "eventTimestamp": "2023-10-27T10:30:00Z"
}
```

**Example using `curl`:**

```bash
curl -X POST \
  http://localhost:8080/api/v1/vehicles/VEHICLE_123/status \
  -H 'Content-Type: application/json' \
  -d '{
    "longitude": -74.0060,
    "latitude": 40.7128,
    "batteryPercentage": 85.5,
    "speed": 65.0,
    "eventTimestamp": "2023-10-27T10:30:00Z"
  }'
```

This will publish the status update via Redis and trigger processing to update the current status and historical logs.

### Retrieving Vehicle Information 📍

-   **Get Current Status for a Vehicle:**
    ```bash
    curl http://localhost:8080/api/v1/vehicles/VEHICLE_123/status
    ```

-   **Get All Vehicles' Current Status:**
    ```bash
    curl http://localhost:8080/api/v1/vehicles/status
    ```

-   **Get Historical Status for a Vehicle:**
    ```bash
    curl http://localhost:8080/api/v1/vehicles/VEHICLE_123/history
    ```

## API Reference 🔗

**Base URL:** `http://localhost:8080/api/v1/vehicles`

| Method  | Endpoint              | Description                                     |
| :------ | :-------------------- | :---------------------------------------------- |
| `POST`  | `/{vehicleId}/status` | Update the status of a specific vehicle.        |
| `GET`   | `/{vehicleId}/status` | Retrieve the current status of a specific vehicle. |
| `GET`   | `/status`             | Retrieve the current status of all vehicles.    |
| `GET`   | `/{vehicleId}/history`| Retrieve the historical status of a vehicle.    |

**Request Body (for POST `/status`):**
```json
{
  "longitude": double,
  "latitude": double,
  "batteryPercentage": double,
  "speed": double,
  "eventTimestamp": string (ISO 8601 format)
}
```

**Response Body (for GET `/status` and `/{vehicleId}/status`):**
```json
{
  "vehicleId": string,
  "longitude": double,
  "latitude": double,
  "batteryPercentage": double,
  "speed": double,
  "eventTimestamp": string (ISO 8601 format)
}
```

**Response Body (for GET `/{vehicleId}/history`):**
```json
[
  {
    "vehicleStatusHistoryId": string,
    "vehicleId": string,
    "latitude": double,
    "longitude": double,
    "batteryPercentage": double,
    "speed": double,
    "eventTimestamp": string (ISO 8601 format),
    "receivedAt": string (ISO 8601 format)
  }
  // ... more history records
]
```

## Project Structure 📁

```
fleet-management-service/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/shanoon/fleetmanagementsystem/
│   │   │       ├── config/
│   │   │       │   ├── RedisCacheConfig.java
│   │   │       │   └── RedisPubSubConfig.java
│   │   │       ├── controller/
│   │   │       │   └── VehicleController.java
│   │   │       ├── message/
│   │   │       │   ├── consumer/
│   │   │       │   │   ├── VehicleCurrentStatusSubscriber.java
│   │   │       │   │   └── VehicleHistoricalDataSubscriber.java
│   │   │       │   └── sender/
│   │   │       │       └── VehicleStatusPublisher.java
│   │   │       ├── model/
│   │   │       │   ├── VehicleStatus.java
│   │   │       │   └── VehicleStatusHistory.java
│   │   │       ├── model/dto/
│   │   │       │   └── VehicleStatusUpdate.java
│   │   │       ├── repository/
│   │   │       │   ├── IVehicleHistoryRepository.java
│   │   │       │   └── IVehicleRepository.java
│   │   │       └── FleetManagementSystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── io/shanoon/fleetmanagementsystem/
│               └── FleetManagementSystemApplicationTests.java
├── README.md
├── compose.yaml
└── settings.gradle
```

## Contributing 🤝

Contributions are welcome! Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature (`git checkout -b feature/YourFeature`).
3.  Commit your changes (`git commit -am 'Add some feature'`).
4.  Push to the branch (`git push origin feature/YourFeature`).
5.  Open a Pull Request.

Please ensure your code adheres to the project's coding standards and includes relevant tests.

## License 📄

No license information was found. Please consider adding a license file (e.g., LICENSE.md) to your repository.

## Important links 🌐

-   **Repository:** [https://github.com/shanooni/fleet-management-service](https://github.com/shanooni/fleet-management-service)

## Footer 👋

This project was developed by shanooni.

[![GitHub stars](https://img.shields.io/github/stars/shanooni/fleet-management-service?style=social)](https://github.com/shanooni/fleet-management-service/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/shanooni/fleet-management-service?style=social)](https://github.com/shanooni/fleet-management-service/forks)

Feel free to **star** ⭐, **fork** 🍴, and **contribute** to this project!



---
**<p align="center">Generated by [ReadmeCodeGen](https://www.readmecodegen.com/)</p>**
