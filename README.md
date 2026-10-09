# Fleet Management Service 🚚

This project is a Java-based Spring Boot application designed to manage vehicle statuses in real-time. It leverages Redis for message queuing and caching of current vehicle statuses, and uses a relational database for storing historical status data.

## Table of Contents 📄

- [Project Title & Badges](#project-title--badges)
- [Description](#description)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [License](#license)
- [Important Links](#important-links)
- [Footer](#footer)

## Project Title & Badges 🏆

# fleet-management-service

## Description 📝

The Fleet Management Service is a robust backend application built with Spring Boot that handles real-time tracking and management of vehicle statuses. It facilitates the ingestion of status updates, processing of this data, and providing endpoints to retrieve current vehicle information. The system utilizes Redis Pub/Sub for efficient real-time communication of status changes and a relational database for persistent storage of historical vehicle data.

## Features ✨

- **Real-time Status Updates:** Processes and broadcasts vehicle status updates (location, battery, speed) in real-time using Redis Pub/Sub.
- **Status Persistence:** Stores historical vehicle status data in a relational database.
- **Current Status Management:** Manages and provides access to the latest known status of each vehicle.
- **API Endpoints:** Exposes RESTful APIs for updating and retrieving vehicle statuses.
- **Data Validation:** Includes validation for incoming vehicle status data to ensure data integrity.

## Tech Stack 🛠️

- **Languages:** Java
- **Frameworks:** Spring Boot, TypeScript (implied by usage in front-end, though not directly in backend analysis)
- **Database:** Relational Database (e.g., PostgreSQL, MySQL - inferred from JPA usage)
- **Caching/Messaging:** Redis
- **Build Tool:** Gradle

## Installation 🚀

This project requires a Java Development Kit (JDK) and a build tool like Gradle. Additionally, a running Redis instance and a relational database are necessary for full functionality.

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/shanoon/fleet-management-service.git
    cd fleet-management-service
    ```

2.  **Set up Dependencies:**
    The project uses Gradle. You can build it using:
    ```bash
    ./gradlew build
    ```

3.  **Configure Application Properties:**
    Create or modify the `src/main/resources/application.properties` file to configure your:
    *   Database connection (URL, username, password)
    *   Redis connection (host, port)
    *   Other application settings

    Example `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/fleetdb
    spring.datasource.username=user
    spring.datasource.password=password
    spring.jpa.hibernate.ddl-auto=update
    spring.redis.host=localhost
    spring.redis.port=6379
    ```

4.  **Run the Application:**
    ```bash
    ./gradlew bootRun
    ```

## Usage 🚗

This service acts as a backend for a fleet management system. It can receive real-time status updates from vehicles and serve this information to a frontend application or other services.

### Real-world Use Cases:

- **Real-time Vehicle Tracking:** Monitor the live location, speed, and battery status of a fleet of vehicles.
- **Fleet Performance Analysis:** Store and analyze historical data to optimize routes, fuel consumption, and maintenance schedules.
- **Alerting System:** Trigger alerts based on predefined conditions (e.g., low battery, unusual speed).

### How to Use:

1.  **Update Vehicle Status:**
    Send a POST request to the `/api/v1/vehicles/{vehicleId}/status` endpoint with the vehicle's current status information.

    **Example Request:**
    ```bash
    curl -X POST \
      http://localhost:8080/api/v1/vehicles/vehicle-123/status \
      -H 'Content-Type: application/json' \
      -d '{
        "longitude": -74.0060,
        "latitude": 40.7128,
        "batteryPercentage": 85.5,
        "speed": 60.0,
        "eventTimestamp": "2023-10-27T10:30:00Z"
      }'
    ```

2.  **Get Current Vehicle Status:**
    Send a GET request to the `/api/v1/vehicles/{vehicleId}/status` endpoint to retrieve the latest known status of a specific vehicle.

    **Example Request:**
    ```bash
    curl -X GET http://localhost:8080/api/v1/vehicles/vehicle-123/status
    ```

    **Example Response:**
    ```json
    {
      "vehicleId": "vehicle-123",
      "longitude": -74.0060,
      "latitude": 40.7128,
      "batteryPercentage": 85.5,
      "speed": 60.0,
      "eventTimestamp": "2023-10-27T10:30:00Z"
    }
    ```

*Note: The `vehicleId` in the request body for status updates is currently ignored and the `vehicleId` from the path parameter is used.*

## Project Structure 🌳

```
fleet-management-service/
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/
│   │   │       └── shanoon/
│   │   │           └── fleetmanagementsystem/
│   │   │               ├── config/
│   │   │               │       └── RedisConfig.java
│   │   │               ├── controller/
│   │   │               │       └── VehicleController.java
│   │   │               ├── message/
│   │   │               │       ├── consumer/
│   │   │               │       │       ├── VehicleCurrentStatusSubscriber.java
│   │   │               │       │       └── VehicleHistoricalDataSubscriber.java
│   │   │               │       └── sender/
│   │   │               │               └── VehicleStatusPublisher.java
│   │   │               ├── model/
│   │   │               │       ├── dto/
│   │   │               │       │       └── VehicleStatusUpdate.java
│   │   │               │       ├── VehicleStatus.java
│   │   │               │       └── VehicleStatusHistory.java
│   │   │               ├── repository/
│   │   │               │       ├── IVehicleHistoryRepository.java
│   │   │               │       └── IVehicleRepository.java
│   │   │               └── service/
│   │   │                   ├── Interface/
│   │   │                   │       └── IVehicleService.java
│   │   │                   └── VehicleService.java
│   │   ├── main/java/io/shanoon/fleetmanagementsystem/FleetManagementSystemApplication.java
│   │   └── resources/
│   │           └── application.properties
│   └── test/
│       └── java/
│           └── io/
│               └── shanoon/
│                   └── fleetmanagementsystem/
│                           └── FleetManagementSystemApplicationTests.java
├── compose.yaml
├── gradlew
├── gradlew.bat
└── settings.gradle
```

## API Reference 🌐

### Vehicle Controller

Base URL: `/api/v1/vehicles`

-   **`POST /{vehicleId}/status`**
    Updates the status of a specific vehicle.
    *   **Path Parameters:**
        *   `vehicleId` (string, required): The ID of the vehicle.
    *   **Request Body:** `VehicleStatusUpdate` (JSON)
        ```json
        {
          "longitude": -74.0060,
          "latitude": 40.7128,
          "batteryPercentage": 85.5,
          "speed": 60.0,
          "eventTimestamp": "2023-10-27T10:30:00Z"
        }
        ```
    *   **Response:** `202 Accepted` (empty body)

-   **`GET /{vehicleId}/status`**
    Retrieves the current status of a specific vehicle.
    *   **Path Parameters:**
        *   `vehicleId` (string, required): The ID of the vehicle.
    *   **Response:** `200 OK` with `VehicleStatus` (JSON) or `404 Not Found` if the vehicle is not found.

    ```json
    {
      "vehicleId": "vehicle-123",
      "longitude": -74.0060,
      "latitude": 40.7128,
      "batteryPercentage": 85.5,
      "speed": 60.0,
      "eventTimestamp": "2023-10-27T10:30:00Z"
    }
    ```

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request or open an issue.

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature-name`).
3.  Make your changes.
4.  Commit your changes (`git commit -m 'Add some feature'`).
5.  Push to the branch (`git push origin feature/your-feature-name`).
6.  Open a Pull Request.

## License 📜

No license information was found. Please consider adding a LICENSE file to your repository.

## Important Links 🔗

-   **Repository:** [https://github.com/shanoon/fleet-management-service](https://github.com/shanoon/fleet-management-service)

## Footer 👋

---

© 2023 **Fleet Management Service** | Repository: [shanoon/fleet-management-service](https://github.com/shanoon/fleet-management-service)

Feel free to **star ⭐**, **fork 🍴**, or **report issues ❗** on the GitHub repository.


---
**<p align="center">Generated by [ReadmeCodeGen](https://www.readmecodegen.com/)</p>**
