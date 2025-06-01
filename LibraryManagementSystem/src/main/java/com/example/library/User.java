package com.example.library;

import java.util.ArrayList; // For borrowedBooks list
import java.util.List;    // For borrowedBooks list

/**
 * Represents a library user.
 */
public class User {
    private final String userId; // Unique identifier for the user
    private final String name;
    private final List<Book> borrowedBooks;
    public static final int MAX_BORROW_LIMIT = 3; // Class constant for borrowing limit

    /**
     * Constructs a new User.
     * @param userId The unique ID of the user (must not be null or empty).
     * @param name The name of the user (must not be null or empty).
     */
    public User(String userId, String name) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>(); // Initialize the list
    }

    // --- Getters ---
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    // More methods will be added in the next chunk
}
