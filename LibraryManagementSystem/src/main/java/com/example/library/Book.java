package com.example.library;

import java.util.Objects; // <-- Add this import

/**
 * Represents a book in the library.
 * Each book has an ISBN, title, author, and availability status.
 */
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private boolean isAvailable; // <-- Add this field

    /**
     * Constructs a new Book.
     * @param isbn The ISBN of the book (must not be null or empty).
     * @param title The title of the book (must not be null or empty).
     * @param author The author of the book (must not be null or empty).
     */
    public Book(String isbn, String title, String author) {
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
        this.isAvailable = true; // <-- Initialize here
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

    public boolean isAvailable() { // <-- Add getter for isAvailable
        return isAvailable;
    }

    // --- Setter ---
    public void setAvailable(boolean available) { // <-- Add setter for isAvailable
        isAvailable = available;
    }

    /**
     * Provides a string representation of the Book object.
     * @return A string containing the book's details.
     */
    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * Two books are considered equal if their ISBNs are the same.
     * @param o The reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    /**
     * Returns a hash code value for the object.
     * This method is supported for the benefit of hash tables such as those provided by HashMap.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}