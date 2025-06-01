# Library Management System 📚

This project is a simple command-line-based Library Management System implemented in Java. It utilizes Maven for project management and JUnit 5 for unit testing. The system allows basic library operations such as managing books and users, issuing books, returning books, and searching the catalog.

## Project Overview

### What it Does

The Library Management System provides functionalities to:

* Add new books to the library catalog.
* Register new users.
* Allow registered users to borrow available books up to a certain limit.
* Allow users to return borrowed books.
* Search for books by ISBN, title, or author.
* Check book availability and user borrowing eligibility.

### How it Works

The system is built around three core classes:

1. **`Book.java`**: Represents a book with attributes like ISBN, title, author, and availability status, including validation.
2. **`User.java`**: Represents a library user with user ID, name, and a managed list of borrowed books with enforced borrowing limits.
3. **`Library.java`**: Manages books and users, including issuing, returning, and searching functionalities using methods like `findBookByIsbn`, `issueBook`, and `returnBook`.

## Clean Code Practices

The project emphasizes clean code practices such as:

1. **Meaningful Names and Input Validation (`Book.java`)**: Constructor validations ensure valid book states at creation.
2. **Encapsulation (`User.java`)**: Protecting internal states using immutable lists.
3. **Using Optional (`Library.java`)**: Clearly handling potentially absent entities using Java’s `Optional` class.

## Unit Testing (`LibraryTest.java`)

Unit tests are written using JUnit 5, ensuring:

* Isolation and clarity using `@BeforeEach` and descriptive test names.
* Coverage of positive and negative scenarios across key functionalities including book issuing, returning, and searching.

## Maven Dependencies

Dependencies managed by Maven primarily include:

* **JUnit 5 Jupiter:** `junit-jupiter-api`, `junit-jupiter-engine`, and `junit-jupiter-params` from Maven Central Repository.

## Challenges Encountered During Development

Several technical challenges addressed include:

1. **Initial Maven Setup:** Ensured project creation included a valid `pom.xml`.
2. **Maven Dependency Issues:** Resolved IDE synchronization problems to enable dependency resolution.
3. **JUnit Version Incompatibilities:** Transitioned tests from JUnit 3 to JUnit 5.
4. **Git Remote and Repository Management:** Fixed remote repository configurations and resolved push conflicts.
5. **GitHub Actions Workflow:** Verified and debugged workflow YAML configuration to trigger CI builds correctly.

These experiences significantly enhanced understanding of Maven, IDE synchronization, version control, and CI/CD setup.
