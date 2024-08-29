# Library-Management-System

## Overview

This project is a **Library Management System** built using **Spring Boot** and **MySQL**. It provides an API for librarians to manage books, patrons, and borrowing records within the library. The system handles CRUD operations for managing library entities and tracks the borrowing and returning of books by patrons.

## Features

- **Book Management**: Add, update, delete, and retrieve book details.
- **Patron Management**: Manage library patrons, including adding, updating, and deleting records.
- **Borrowing Management**: Handle book borrowing and return processes.
- **Validation & Error Handling**: Robust input validation and meaningful error messages.
- **Security**:Basic authentication using JWT for API protection.
- **Transaction Management**: Ensure data integrity during critical operations.
- **Unit Testing**: Comprehensive tests to ensure API functionality.

## Technologies Used

- **Spring Boot**: Backend framework for building the RESTful API.
- **MySQL**: Relational database for persisting library data.
- **JUnit & Mockito**: Testing frameworks for unit tests.
- **Maven**: Dependency management and build automa

### Entities

- **Book**: Includes attributes like ID, title, author, publication year, ISBN.
- **Patron**: Contains details like ID, name, and contact information.
- **Borrowing Record**: Tracks the association between books and patrons, including borrowing and return dates.

### API Endpoints

- **Book Management**:
  - `GET /api/books`: Retrieve a list of all books.
  - `GET /api/books/{id}`: Retrieve details of a specific book by ID.
  - `POST /api/books`: Add a new book to the library.
  - `PUT /api/books/{id}`: Update an existing book's information.
  - `DELETE /api/books/{id}`: Remove a book from the library.
- **Patron Management**:
  - `GET /api/patrons`: Retrieve a list of all patrons.
  - `GET /api/patrons/{id}`: Retrieve details of a specific patron by ID.
  - `POST /api/patrons`: Add a new patron to the system.
  - `PUT /api/patrons/{id}`: Update an existing patron's information.
  - `DELETE /api/patrons/{id}`: Remove a patron from the system.
- **Borrowing Management**:
  - `POST /api/borrow/{bookId}/patron/{patronId}`: Allow a patron to borrow a book.
  - `PUT /api/return/{bookId}/patron/{patronId}`: Record the return of a borrowed book by a patron.

## Getting Started

### Prerequisites

- **Java 8** or higher
- **MySQL**
- **Maven**

### Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Mariam-Mokhtar/library-management-system.git

2. **Navigate to the project directory in your terminal**:
3. **Run the Application:**:
   ```bash
    mvn spring-boot:run
4. **Update the MySQL configuration in the application.properties file**:
   ```bash
    spring.datasource.url=jdbc:mysql://<your-database-url>:<port>/<database-name>?useSSL=false&serverTimezone=UTC
    spring.datasource.username=<your-database-username>
    spring.datasource.password=<your-database-password>
5. **For Run unit tests using Maven**:
   ```bash
    mvn test

### Documentation 
http://localhost:8080/swagger-ui.html
