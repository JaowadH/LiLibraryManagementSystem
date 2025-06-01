package com.example.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
// No new imports needed for this chunk

/**
 * Manages a collection of books and users.
 */
public class Library {
    private final Map<String, Book> bookCatalog; // ISBN -> Book
    private final Map<String, User> registeredUsers; // UserID -> User

    public Library() {
        this.bookCatalog = new HashMap<>();
        this.registeredUsers = new HashMap<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            System.err.println("Attempted to add a null book.");
            return;
        }
        bookCatalog.putIfAbsent(book.getIsbn(), book);
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookCatalog.get(isbn));
    }

    /**
     * Registers a new user in the library system.
     * If a user with the same ID already exists, they will not be re-registered.
     * @param user The user to register (must not be null).
     */
    public void registerUser(User user) {
        if (user == null) {
            System.err.println("Attempted to register a null user."); // Or throw exception
            return;
        }
        registeredUsers.putIfAbsent(user.getUserId(), user);
    }

    /**
     * Finds a registered user by their ID.
     * @param userId The ID of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
    _*/
    public Optional<User> findUserById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(registeredUsers.get(userId));
    }
}