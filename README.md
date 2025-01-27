# Library Management System

This repository contains the source code for a **Library Management System** developed using JavaFX. The project provides a user-friendly interface to manage books, users, and transactions in a library environment.

---

## Features

- **User Management**:
  - Add, update, and delete user information.
  - Role-based access control (e.g., Admin, Librarian).

- **Book Management**:
  - Add, update, and delete books.
  - Search for books by title, author, or category.

- **Transaction Management**:
  - Borrow and return books.
  - Track overdue items.

- **Dashboard**:
  - Overview of library statistics.
  - Notifications for overdue items.

---

## Technologies Used

- **Java**: Programming language.
- **JavaFX**: For building the graphical user interface.
- **MySQL**: Database for storing library data.
- **Maven**: For project dependency management.

---

## Prerequisites

Ensure you have the following installed on your system:

1. **Java Development Kit (JDK)**: Version 11 or higher.
2. **MySQL**: For database management.
3. **Maven**: For building the project.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/zhalgas-seidazym/library-system-javafx.git
   cd library-system-javafx
   ```

2. **Database Setup**:
   - Create a new MySQL database (e.g., `library_db`).
   - Import the provided SQL file (`database/library_db.sql`) to set up the schema and initial data.

3. **Configure Database Connection**:
   - Update the `db.properties` file in the `resources` directory with your database credentials:
     ```properties
     db.url=jdbc:mysql://localhost:3306/library_db
     db.username=your_username
     db.password=your_password
     ```

4. **Build and Run**:
   - Build the project using Maven:
     ```bash
     mvn clean install
     ```
   - Run the application:
     ```bash
     mvn javafx:run
     ```

---

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── controllers/   # JavaFX controllers for UI components
│   │   ├── models/        # Business logic and database models
│   │   ├── views/         # FXML files for UI layouts
│   │   └── utils/         # Utility classes and helpers
│   └── resources/
│       ├── database/      # SQL scripts
│       ├── fxml/          # FXML files
│       └── css/           # Stylesheets for UI
└── test/
    └── java/              # Unit tests
```

---

## Contribution Guidelines

We welcome contributions! To contribute:

1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add new feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.

---

## Contact

For any queries or issues, please contact [Zhalgas Seidazym](https://github.com/zhalgas-seidazym).
