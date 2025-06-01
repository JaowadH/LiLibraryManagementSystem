package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// We'll add more imports as we add more assertions
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional; // For testing findBookByIsbn

/**
 * Unit tests for the Library class.
 */
class LibraryTest {

    private Library library;
    private Book book1, book2, book3; // book3 will be used for adding
    private User user1;

    /**
     * Sets up the test environment before each test method.
     * Initializes the library and some sample books and users.
     */
    @BeforeEach
    void setUp() {
        library = new Library();

        // Initialize some books
        book1 = new Book("978-0321765723", "Effective Java", "Joshua Bloch");
        book2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin");
        book3 = new Book("111-2223334445", "The Pragmatic Programmer", "Andy Hunt"); // For adding test

        // Add initial books to the library
        library.addBook(book1);
        library.addBook(book2);

        // Initialize a user
        user1 = new User("user001", "Alice Smith");
        library.registerUser(user1);
    }

    // Test methods will be added in the next chunk
}