package com.example.library;

import java.util.ArrayList;
import java.util.Collections; // <-- Add this import
import java.util.List;
import java.util.Objects;    // <-- Add this import

/**
 * Represents a library user.
 * Each user has a unique ID, name, and a list of books they have borrowed.
 */
public class User {
    private final String userId;
    private final String name;
    private final List<Book> borrowedBooks;
    public static final int MAX_BORROW_LIMIT = 3;

    public User(String userId, String name) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        this.userId = userId;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // --- Getters ---
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable view of the list of borrowed books.
     * This prevents external modification of the internal list.
     * @return An unmodifiable list of borrowed books.
     */
    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks); // Encapsulation
    }

    /**
     * Adds a book to the user's list of borrowed books.
     * @param book The book to be added (must not be null).
     * @return true if the book was added successfully, false otherwise (e.g., if borrow limit reached or book is null).
     */
    public boolean borrowBook(Book book) {
        if (book == null) {
            // Optionally, throw new IllegalArgumentException("Book to borrow cannot be null.");
            // Or handle as per requirements, for now, just returning false
            return false;
        }
        if (borrowedBooks.size() < MAX_BORROW_LIMIT && !borrowedBooks.contains(book)) {
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    /**
     * Removes a book from the user's list of borrowed books.
     * @param book The book to be returned (must not be null).
     * @return true if the book was successfully returned, false otherwise (e.g., if the user didn't borrow this book or book is null).
     */
    public boolean returnBook(Book book) {
        if (book == null) {
            return false;
        }
        return borrowedBooks.remove(book);
    }

    /**
     * Checks if the user has reached their borrowing limit.
     * @return true if the user can borrow more books, false otherwise.
     */
    public boolean canBorrowMore() {
        return borrowedBooks.size() < MAX_BORROW_LIMIT;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", borrowedBooksCount=" + borrowedBooks.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}