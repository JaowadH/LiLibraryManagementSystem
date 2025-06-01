package com.example.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Manages a collection of books and users, and handles book issuing and returning.
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
            System.err.println("Attempted to add a null book."); // Or throw new IllegalArgumentException
            return;
        }
        bookCatalog.putIfAbsent(book.getIsbn(), book);
    }

    /**
     * Registers a new user in the library system.
     * If a user with the same ID already exists, they will not be re-registered.
     * @param user The user to register (must not be null).
     */
    public void registerUser(User user) {
        if (user == null) {
            System.err.println("Attempted to register a null user."); // Or throw new IllegalArgumentException
            return;
        }
        registeredUsers.putIfAbsent(user.getUserId(), user);
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

    /**
     * Finds books in the catalog by their title.
     * Performs a case-insensitive partial match.
     * @param title The title to search for.
     * @return A list of books matching the title. Returns an empty list if title is null or blank.
     */
    public List<Book> findBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>(); // Return empty list for invalid input
        }
        String lowerCaseTitle = title.toLowerCase();
        return bookCatalog.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerCaseTitle))
                .collect(Collectors.toList());
    }

    /**
     * Finds books in the catalog by their author.
     * Performs a case-insensitive partial match.
     * @param author The author to search for.
     * @return A list of books by the author. Returns an empty list if author is null or blank.
     */
    public List<Book> findBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String lowerCaseAuthor = author.toLowerCase();
        return bookCatalog.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lowerCaseAuthor))
                .collect(Collectors.toList());
    }


    /**
     * Finds a registered user by their ID.
     * @param userId The ID of the user to find.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<User> findUserById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(registeredUsers.get(userId));
    }

    /**
     * Issues a book to a user.
     * @param userId The ID of the user borrowing the book.
     * @param isbn The ISBN of the book to be issued.
     * @return true if the book was successfully issued, false otherwise
     * (e.g., book not found, book unavailable, user not found, user at borrow limit).
     */
    public boolean issueBook(String userId, String isbn) {
        Optional<User> userOpt = findUserById(userId);
        Optional<Book> bookOpt = findBookByIsbn(isbn);

        if (userOpt.isPresent() && bookOpt.isPresent()) {
            User user = userOpt.get();
            Book book = bookOpt.get();

            if (book.isAvailable() && user.canBorrowMore()) {
                // User.borrowBook checks if the book is already borrowed by this user and limit
                if (user.borrowBook(book)) {
                    book.setAvailable(false);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a book from a user to the library.
     * @param userId The ID of the user returning the book.
     * @param isbn The ISBN of the book to be returned.
     * @return true if the book was successfully returned, false otherwise
     * (e.g., user not found, book not found, user did not borrow this book).
     */
    public boolean returnBook(String userId, String isbn) {
        Optional<User> userOpt = findUserById(userId);
        Optional<Book> bookOpt = findBookByIsbn(isbn);

        if (userOpt.isPresent() && bookOpt.isPresent()) {
            User user = userOpt.get();
            Book book = bookOpt.get();

            // User.returnBook handles removing from its list
            // It will return false if the user didn't have this book
            if (user.returnBook(book)) {
                book.setAvailable(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a specific book is currently available for borrowing.
     * @param isbn The ISBN of the book.
     * @return true if the book is available, false otherwise or if the book doesn't exist.
     */
    public boolean isBookAvailable(String isbn) {
        return findBookByIsbn(isbn)
                .map(Book::isAvailable) // If book exists, get its availability
                .orElse(false);         // If book doesn't exist, it's not available
    }


    /**
     * Checks if a user has reached their borrowing limit or can borrow more.
     * @param userId The ID of the user.
     * @return true if the user can borrow more books, false if they reached the limit or user not found.
     */
    public boolean canUserBorrow(String userId) {
        return findUserById(userId)
                .map(User::canBorrowMore) // If user exists, check their borrow status
                .orElse(false);          // If user doesn't exist, they can't borrow
    }

    /**
     * Gets a list of all books in the catalog.
     * @return A new list containing all books.
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookCatalog.values());
    }

    /**
     * Gets a list of all registered users.
     * @return A new list containing all users.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(registeredUsers.values());
    }
}