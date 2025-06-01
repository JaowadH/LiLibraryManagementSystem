package com.example.library;

/**
 * Represents a book in the library.
 */
public class Book {
    private final String isbn; // Unique identifier for the book
    private final String title;
    private final String author;
    // 'isAvailable' will be added in the next chunk

    /**
     * Constructs a new Book.
     * @param isbn The ISBN of the book (must not be null or empty).
     * @param title The title of the book (must not be null or empty).
     * @param author The author of the book (must not be null or empty).
     */
    public Book(String isbn, String title, String author) {
        // Input validation
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("Book ISBN cannot be null or empty.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty.");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be null or empty.");
        }
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        // this.isAvailable = true; // Will be added in the next chunk
    }

    // --- Getters ---
    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
