# CRUD & API Test

## Overview

This project showcases a Spring Boot 3 application integrated with PostgreSQL 12, Docker, and developed using Java 17.
The primary features of the project include user management operations such as creating users, retrieving all users,
fetching a user by ID, and deleting a user by ID. The flexibility of the project is evident in its support for both JSON
and XML formats in the request body for creating users.

## Technologies Used

- Spring Boot 3
- PostgreSQL 12
- Docker
- Java 17

## Features

1. **Create User**
2. **Get All Users**
3. **Get User by ID**
4. **Delete User by ID**

## Prerequisites

Before running the project, ensure that you have the following software installed on your machine:

- Docker
- Java 17

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/aagun/ediindoensia_technical_test_java_developer
   ```
2. **Navigate to the Project Directory:**
    ```bash
    cd ediindoensia_technical_test_java_developer 
   ```
3. **Run Docker Compose**
    ```bash
    docker-compose up -d 
   ```
4. **Run Spring Boot Application**
    ```bash
    ./mvnw spring-boot:run 
   ```
5. **Testing Endpoints:**

   Use Postman to test the following endpoints:
   - **Create User:**
     - **URL:** `http://localhost:8080/api/users`
     - **Method:** `POST`
     - **Request Body:**
     ```json
     {
         "userid": {userid},
         "namalengkap": {fullname},
         "username": {username},
         "password": {password},
         "status": {status}
     }
     ```
   - **Create User:**
     - **URL:** `http://localhost:8080/api/users`
     - **Method:** `POST`
     - **Request Body:**
     ```xml
         <user>
             <userid>{userid}</userid>
             <namalengkap>{fullName}</namalengkap>
             <username>{username}</username>
             <password>{password}</password>
             <status>{status}</status>
         </user>
     ```
   - **Get All Users:**
     - **URL:** `http://localhost:8080/api/users/all`
     - **Method:** `GET`

   - **Get User By User Id:**
     - **URL:** `http://localhost:8080/api/users/{userId}`
     - **Method:** `GET`
   
   - **DELETE User By User Id:**
     - **URL:** `http://localhost:8080/api/users/{userId}`
     - **Method:** `DELETE`

6. **Stopping the Project:**

   When you're done, stop the Spring Boot application and the Docker containers using the following commands:

    - **Stop the Spring Boot application:**
        ```bash
        # Press Ctrl + C in the terminal where the application is running
        ```

    - **Stop the Docker containers:**
        ```bash
        docker-compose down -d --remove-orphans
        ```

## Notes

- Make sure to replace placeholders like `{userid}`, `{fullName}`, `{username}` and etc.. with your desired values.
- Ensure Docker and Docker Compose are running before starting the application.
- You may customize the database configurations in the `application.yml` file if needed.

## License

This project is licensed under the [MIT License](LICENSE).
