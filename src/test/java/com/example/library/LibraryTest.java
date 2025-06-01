package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Library class.
 */
class LibraryTest {

    private Library library;
    private Book book1, book2, book3, book4, bookForLimit;
    private User user1, user2;

    /**
     * Sets up the test environment before each test method.
     * Initializes the library, books, and users.
     */
    @BeforeEach
    void setUp() {
        library = new Library();

        // Initialize some books
        book1 = new Book("978-0321765723", "Effective Java", "Joshua Bloch");
        book2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin");
        book3 = new Book("978-0134685991", "Domain-Driven Design", "Eric Evans"); // Also for title/author search
        book4 = new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman"); // For adding test
        bookForLimit = new Book("978-0201633610", "Design Patterns", "Erich Gamma"); // For borrow limit test


        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        // book4 and bookForLimit are not added initially for specific tests

        // Initialize some users
        user1 = new User("user001", "Alice Smith");
        user2 = new User("user002", "Bob Johnson");

        library.registerUser(user1);
        library.registerUser(user2);
    }

    @Test
    @DisplayName("Test adding a new book to the library")
    void addBook_newBook_shouldBeInCatalogAndList() {
        int initialBookCount = library.getAllBooks().size();
        library.addBook(book4); // book4 is not in setUp
        Optional<Book> foundBookOpt = library.findBookByIsbn(book4.getIsbn());

        assertEquals(initialBookCount + 1, library.getAllBooks().size(), "Library book count should increment.");
        assertTrue(foundBookOpt.isPresent(), "Book should be found after adding.");
        assertEquals(book4, foundBookOpt.get(), "The found book should be the one that was added.");
        assertTrue(library.getAllBooks().contains(book4), "Library's book list should contain the new book.");
    }

    @Test
    @DisplayName("Test adding a duplicate book (same ISBN) to the library should not change catalog")
    void addBook_duplicateBook_shouldNotBeAddedOrOverwrite() {
        int initialBookCount = library.getAllBooks().size();
        Book duplicateBook = new Book(book1.getIsbn(), "Another Book Title", "Another Author"); // book1's ISBN
        library.addBook(duplicateBook);

        assertEquals(initialBookCount, library.getAllBooks().size(), "Book count should not change when adding a duplicate ISBN.");
        Optional<Book> foundBook = library.findBookByIsbn(book1.getIsbn());
        assertTrue(foundBook.isPresent());
        assertEquals(book1.getTitle(), foundBook.get().getTitle(), "Original book's title should remain unchanged.");
    }

    @Test
    @DisplayName("Test finding an existing book by ISBN")
    void findBookByIsbn_existingBook_shouldReturnBook() {
        Optional<Book> foundBook = library.findBookByIsbn(book1.getIsbn());
        assertTrue(foundBook.isPresent(), "Book should be found by its ISBN.");
        assertEquals(book1, foundBook.get(), "The correct book should be returned.");
    }

    @Test
    @DisplayName("Test finding a non-existing book by ISBN should return empty Optional")
    void findBookByIsbn_nonExistingBook_shouldReturnEmptyOptional() {
        Optional<Book> foundBook = library.findBookByIsbn("000-0000000000"); // Non-existent ISBN
        assertFalse(foundBook.isPresent(), "No book should be found for a non-existing ISBN.");
    }

    @Test
    @DisplayName("Test finding books by title (case-insensitive, partial match)")
    void findBooksByTitle_partialMatch_shouldReturnMatchingBooks() {
        List<Book> foundBooksByJava = library.findBooksByTitle("java"); // "Effective Java"
        assertEquals(1, foundBooksByJava.size(), "Should find one book with 'java' in the title.");
        assertTrue(foundBooksByJava.contains(book1), "The list should contain 'Effective Java'.");

        List<Book> foundBooksByDesign = library.findBooksByTitle("Design"); // "Domain-Driven Design"
        assertEquals(1, foundBooksByDesign.size(), "Should find 'Domain-Driven Design'.");
        assertTrue(foundBooksByDesign.contains(book3));
    }

    @Test
    @DisplayName("Test finding books by a non-matching title should return empty list")
    void findBooksByTitle_noMatch_shouldReturnEmptyList() {
        List<Book> foundBooks = library.findBooksByTitle("NonExistentTitleXYZ");
        assertTrue(foundBooks.isEmpty(), "Should return an empty list for a non-matching title.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @DisplayName("Test finding books by empty or blank title should return empty list")
    void findBooksByTitle_emptyOrBlankQuery_shouldReturnEmptyList(String titleQuery) {
        List<Book> foundBooks = library.findBooksByTitle(titleQuery);
        assertTrue(foundBooks.isEmpty(), "Searching with empty or blank title should return an empty list.");
    }


    @Test
    @DisplayName("Test finding books by author (case-insensitive, partial match)")
    void findBooksByAuthor_partialMatch_shouldReturnMatchingBooks() {
        List<Book> foundByBloch = library.findBooksByAuthor("bloch"); // Joshua Bloch
        assertEquals(1, foundByBloch.size());
        assertTrue(foundByBloch.contains(book1));

        List<Book> foundByMartin = library.findBooksByAuthor("Martin"); // Robert C. Martin
        assertEquals(1, foundByMartin.size());
        assertTrue(foundByMartin.contains(book2));
    }

    @Test
    @DisplayName("Test finding books by a non-matching author should return empty list")
    void findBooksByAuthor_noMatch_shouldReturnEmptyList() {
        List<Book> foundBooks = library.findBooksByAuthor("NonExistentAuthorXYZ");
        assertTrue(foundBooks.isEmpty(), "Should return an empty list for a non-matching author.");
    }

    @Test
    @DisplayName("Test successful book issue to a valid user within limits")
    void issueBook_successfulScenario_shouldUpdateAvailabilityAndUserList() {
        assertTrue(book1.isAvailable(), "Book1 should be initially available.");
        assertTrue(user1.canBorrowMore(), "User1 should be able to borrow.");
        assertEquals(0, user1.getBorrowedBooks().size(), "User1 should have 0 books initially.");

        boolean issued = library.issueBook(user1.getUserId(), book1.getIsbn());

        assertTrue(issued, "Book issuance should be successful.");
        assertFalse(book1.isAvailable(), "Book1 should be unavailable after issuing.");
        assertEquals(1, user1.getBorrowedBooks().size(), "User1 should have 1 book after borrowing.");
        assertTrue(user1.getBorrowedBooks().contains(book1), "User1's borrowed list should contain book1.");
    }

    @Test
    @DisplayName("Test issuing a book that is already issued (unavailable) should fail")
    void issueBook_bookAlreadyIssued_shouldFail() {
        library.issueBook(user1.getUserId(), book1.getIsbn()); // First, issue book1 to user1
        assertTrue(user1.getBorrowedBooks().contains(book1));
        assertFalse(book1.isAvailable());

        // Attempt to issue the same book1 to user2
        boolean issuedAgain = library.issueBook(user2.getUserId(), book1.getIsbn());

        assertFalse(issuedAgain, "Issuing an already issued book should fail.");
        assertFalse(user2.getBorrowedBooks().contains(book1), "User2 should not have borrowed book1.");
        assertEquals(0, user2.getBorrowedBooks().size());
    }

    @Test
    @DisplayName("Test issuing a book to a non-existent user should fail")
    void issueBook_nonExistentUser_shouldFail() {
        boolean issued = library.issueBook("nonExistentUser123", book1.getIsbn());
        assertFalse(issued, "Issuing to a non-existent user should fail.");
        assertTrue(book1.isAvailable(), "Book availability should not change if issue fails.");
    }

    @Test
    @DisplayName("Test issuing a non-existent book to a valid user should fail")
    void issueBook_nonExistentBook_shouldFail() {
        boolean issued = library.issueBook(user1.getUserId(), "000-NON-EXISTENT-ISBN");
        assertFalse(issued, "Issuing a non-existent book should fail.");
        assertEquals(0, user1.getBorrowedBooks().size(), "User's borrowed books count should not change.");
    }

    @Test
    @DisplayName("Test user trying to borrow more books than allowed (borrowing limit) should fail")
    void issueBook_userExceedsBorrowLimit_shouldFail() {
        // Issue 3 books to user1 (MAX_BORROW_LIMIT is 3 in User class)
        assertTrue(library.issueBook(user1.getUserId(), book1.getIsbn()), "Issue 1st book for user1");
        assertTrue(library.issueBook(user1.getUserId(), book2.getIsbn()), "Issue 2nd book for user1");
        assertTrue(library.issueBook(user1.getUserId(), book3.getIsbn()), "Issue 3rd book for user1");

        assertEquals(User.MAX_BORROW_LIMIT, user1.getBorrowedBooks().size(), "User1 should have borrowed max books.");
        assertFalse(user1.canBorrowMore(), "User1 should not be able to borrow more.");

        library.addBook(bookForLimit); // Add another book to library for this test
        // Attempt to issue a 4th book (bookForLimit)
        boolean issuedFourth = library.issueBook(user1.getUserId(), bookForLimit.getIsbn());
        assertFalse(issuedFourth, "Issuing the 4th book should fail due to borrow limit.");
        assertTrue(bookForLimit.isAvailable(), "The 4th book (bookForLimit) should remain available.");
        assertFalse(user1.getBorrowedBooks().contains(bookForLimit), "User1 should not have bookForLimit.");
        assertEquals(User.MAX_BORROW_LIMIT, user1.getBorrowedBooks().size(), "User1's borrowed books count should remain at max limit.");
    }


    @Test
    @DisplayName("Test successful book return")
    void returnBook_successfulScenario_shouldUpdateAvailabilityAndUserList() {
        // First, issue book1 to user1
        library.issueBook(user1.getUserId(), book1.getIsbn());
        assertFalse(book1.isAvailable());
        assertTrue(user1.getBorrowedBooks().contains(book1));

        boolean returned = library.returnBook(user1.getUserId(), book1.getIsbn());

        assertTrue(returned, "Book return should be successful.");
        assertTrue(book1.isAvailable(), "Book1 should be available after return.");
        assertFalse(user1.getBorrowedBooks().contains(book1), "User1 should no longer have book1.");
        assertEquals(0, user1.getBorrowedBooks().size(), "User1's borrowed books count should be 0.");
    }

    @Test
    @DisplayName("Test returning a book not borrowed by the user should fail")
    void returnBook_bookNotBorrowedByUser_shouldFail() {
        // book2 is available and not borrowed by user1
        assertTrue(book2.isAvailable());
        assertFalse(user1.getBorrowedBooks().contains(book2));

        boolean returned = library.returnBook(user1.getUserId(), book2.getIsbn());

        assertFalse(returned, "Returning a book not borrowed by the user should fail.");
        assertTrue(book2.isAvailable(), "Book2's availability should not change.");
    }

    @Test
    @DisplayName("Test returning a non-existent book should fail")
    void returnBook_nonExistentBook_shouldFail() {
        boolean returned = library.returnBook(user1.getUserId(), "000-NON-EXISTENT-ISBN");
        assertFalse(returned, "Returning a non-existent book should fail.");
    }

    @Test
    @DisplayName("Test returning a book by a non-existent user should fail")
    void returnBook_nonExistentUser_shouldFail() {
        // Issue book1 to user1 first to make it unavailable
        library.issueBook(user1.getUserId(), book1.getIsbn());
        assertFalse(book1.isAvailable());

        boolean returned = library.returnBook("nonExistentUser123", book1.getIsbn());
        assertFalse(returned, "Returning a book by a non-existent user should fail.");
        assertFalse(book1.isAvailable(), "Book1 should remain unavailable as return failed.");
    }


    @ParameterizedTest
    @CsvSource({
            "user001, true",  // User exists and can borrow (initially)
            "user003, false"  // User does not exist
    })
    @DisplayName("Test canUserBorrow functionality for various scenarios")
    void canUserBorrow_variousScenarios(String userId, boolean expectedOutcome) {
        if ("user001".equals(userId)) { // Special handling for existing user to test initial state
            assertTrue(library.canUserBorrow(userId), "User001 should initially be able to borrow.");
        } else {
            assertEquals(expectedOutcome, library.canUserBorrow(userId));
        }
    }

    @Test
    @DisplayName("Test isBookAvailable functionality for various scenarios")
    void isBookAvailable_variousScenarios() {
        assertTrue(library.isBookAvailable(book1.getIsbn()), "Book1 should be available initially.");
        library.issueBook(user1.getUserId(), book1.getIsbn()); // Issue book1
        assertFalse(library.isBookAvailable(book1.getIsbn()), "Book1 should be unavailable after issuing.");
        assertFalse(library.isBookAvailable("non-existent-isbn123"), "Non-existent book should not be available.");
    }
}