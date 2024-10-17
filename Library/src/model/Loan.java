package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * The Loan class represents a book loan in a library system. It contains
 * information about the loan such as the unique loan ID, user ID, book ID,
 * loan date, return date, and reservation date. This class provides constructors
 * for initializing loan objects and getter methods to retrieve the loan details.
 */
public class Loan {
    /**
     * Creates a new Loan object with the specified details.
     *
     * @param id The unique identifier for the loan.
     * @param userId The identifier of the user who borrowed the book.
     * @param bookId The identifier of the borrowed book.
     * @param loanDate The date on which the book was borrowed.
     * @param returnDate The date on which the book is due to be returned.
     */
    public Loan(int id, int userId, int bookId, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    /**
     * Initializes a new Loan object using database-stored return date.
     *
     * @param id The unique identifier for the loan.
     * @param userId The identifier of the user who borrowed the book.
     * @param bookId The identifier of the borrowed book.
     * @param loanDate The date on which the book was borrowed.
     * @param returnDateDatabase The return date of the book as stored in the database.
     */
    public Loan(int id, int userId, int bookId, LocalDate loanDate, String returnDateDatabase) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDateDatabase = returnDateDatabase;
    }

    /**
     * Initializes a new Loan object with the specified details.
     *
     * @param id The unique identifier for the loan.
     * @param userId The identifier of the user who reserved the book.
     * @param bookId The identifier of the reserved book.
     * @param reservationDate The date on which the book was reserved.
     */
    public Loan(int id, int userId, int bookId, LocalDate reservationDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.reservationDate = reservationDate;
    }

    /**
     * Returns the unique identifier of the loan.
     *
     * @return
     * */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the date on which the book was reserved.
     *
     * @return The reservation date associated with this loan.
     */
    public LocalDate getReservationDate() {
        return reservationDate;
    }

    /**
     * Returns the identifier of the borrowed book.
     *
     * @return The book identifier associated with this loan.
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Returns the identifier of the user associated with this loan.
     *
     * @return The user identifier.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Retrieves the date on which the book was borrowed.
     *
     * @return The loan date associated with this loan.
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Retrieves the date on which the book is due to be returned.
     *
     * @return The return date associated with this loan.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Retrieves the return date of the book as stored in the database.
     *
     * @return The return date from the database as a String.
     */
    public String getReturnDateDatabase() {
        return returnDateDatabase;
    }

    /**
     * Returns a string representation of the Loan object, including user ID, book ID, loan date, and return date.
     *
     * @return A string that represents the Loan object.
     */
    @Override
    public String toString() {
        return "User ID: " + this.userId + " Book ID: " + this.bookId + " Loan Date: " + this.loanDate + " Return Date: " + this.returnDate;
    }

    private int id;
    private int userId;
    private int bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate reservationDate;
    private String returnDateDatabase = "";
}
