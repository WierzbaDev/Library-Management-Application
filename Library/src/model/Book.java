package model;


import java.util.Date;

/**
 * The Book class represents a book in the library system.
 * It contains information about the book's ID, title, author,
 * availability status, and the date from when it is available.
 */
public class Book {

    /**
     * Constructs a Book with the specified book ID.
     *
     * @param bookId the unique identifier of the book
     */
    public Book(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Constructs a Book with the specified title, author, and availability status.
     *
     * @param title The title of the book.
     * @param author The author of the book.
     * @param available The availability status of the book. If true, the book is available; otherwise, it is not.
     */
    public Book(String title, String author, boolean available) {
        this.title = title;
        this.author = author;
        this.available = available;
    }

    /**
     * Constructs a Book with the specified book ID, title, author, and availability status.
     *
     * @param bookId the unique identifier of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param available the availability status of the book; if true, the book is available, otherwise it is not
     */
    public Book(int bookId, String title, String author, boolean available) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    /**
     * Constructs a Book with the specified book ID, title, author, availability status, and the date from when it is available.
     *
     * @param bookId the unique identifier of the book
     * @param title the title of the book
     * @param author the author of the book
     * @param available the availability status of the book; if true, the book is available, otherwise it is not
     * @param available_from the date from when the book is available
     */
    public Book(int bookId, String title, String author, boolean available, Date available_from) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = available;
        this.available_from = available_from;
    }

    /**
     * Retrieves the unique identifier of the book.
     *
     * @return the unique identifier of the book
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Retrieves the author of the book.
     *
     * @return the author of the book
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Retrieves the date from when the book is available.
     *
     * @return the date from when the book is available
     */
    public Date getAvailable_from() {
        return available_from;
    }

    /**
     * Retrieves the title of the book.
     *
     * @return the title of the book
     */
    public String getTitle() {
        return title;
    }

    /**
     * Checks whether the book is available.
     *
     * @return true if the book is available; false otherwise
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Returns a string representation of the book, including its ID, title, author,
     * and the date from when it is available.
     *
     * @return a string containing the book's ID, title, author, and availability date
     */
    @Override
    public String toString() {
        return "ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Available from: " + available_from;
    }

    private int bookId;
    private String title;
    private String author;
    private boolean available;
    private Date available_from;
}
