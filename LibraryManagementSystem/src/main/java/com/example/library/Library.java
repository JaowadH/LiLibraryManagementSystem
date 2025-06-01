package com.example.library;

import java.util.HashMap; // For catalogs
import java.util.Map;     // For catalogs
import java.util.Optional; // For find methods

/**
 * Manages a collection of books and users.
 */
public class Library {
    private final Map<String, Book> bookCatalog; // ISBN -> Book
    private final Map<String, User> registeredUsers; // UserID -> User

    /**
     * Constructs a new Library with empty catalogs.
     */
    public Library() {
        this.bookCatalog = new HashMap<>();
        this.registeredUsers = new HashMap<>();
    }

    /**
     * Adds a new book to the library catalog.
     * If a book with the same ISBN already exists, it will not be added.
     * @param book The book to add (must not be null).
     */
    public void addBook(Book book) {
        if (book == null) {
            System.err.println("Attempted to add a null book."); // Or throw exception
            return;
        }
        // putIfAbsent ensures we don't overwrite an existing book with the same ISBN
        bookCatalog.putIfAbsent(book.getIsbn(), book);
    }

    /**
     * Finds a book in the catalog by its ISBN.
     * @param isbn The ISBN of the book to find.
     * @return An Optional containing the book if found, otherwise an empty Optional.
     */
    public Optional<Book> findBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(bookCatalog.get(isbn));
    }

    // More methods to come
}