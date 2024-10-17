package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The BookDAO class provides methods to perform CRUD (Create, Read, Update, Delete) operations on the Book database.
 */
public class BookDAO {
    /**
     * Establishes and returns a connection to the library database.
     *
     * @return a Connection object to the library database
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String password = "";
        String user = "root";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Retrieves a list of books that are currently available.
     *
     * @return a list of Book objects that are available in the library
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getAvailableBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Book book;
        String query = "SELECT * FROM book WHERE available = 1";

        try (Connection conn = getConnection();
             Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("available");
                book = new Book(id, title, author, available);
                books.add(book);
            }
        } return books;
    }

    /**
     * Retrieves a list of all books from the library database.
     *
     * @return a list of Book objects representing all books in the library
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        try (Connection conn = getConnection();
        Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("available");
                Date availableFrom = resultSet.getDate("available_from");
                Book book = new Book(id, title, author, available, availableFrom);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Retrieves a list of books that are currently unavailable.
     *
     * @return a list of Book objects representing the unavailable books in the library
     * @throws SQLException if a database access error occurs
     */
    public List<Book> getUnavailableBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        Book book;
        String query = "SELECT * FROM book WHERE available = 0";

        try (Connection conn = getConnection();
        Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("available");
                Date availableFrom = resultSet.getDate("available_from");
                book = new Book(id, title, author, available, availableFrom);
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Adds a new book to the library database.
     *
     * @param book The Book object containing details about the book to be added.
     * @throws SQLException if a database access error occurs.
     */
    public void addNewBook(Book book) throws SQLException {
        String query = "INSERT INTO book (title, author, available) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3,1);
            ps.executeUpdate();
        }
    }

    /**
     * Checks if there is an existing book with the given title in the database.
     *
     * @param title The title of the book to check for existence.
     * @return true if a book with the specified title exists*/
    public boolean isTheSameTitle(String title) throws SQLException {
        String query = "SELECT * FROM book WHERE title = ?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, title);
            ResultSet resultSet = ps.executeQuery();
            return resultSet.next();
        }
    }

    /**
     * Deletes a book from the library database using the specified book ID.
     *
     * @param bookId The unique identifier of the book to be deleted.
     * @throws SQLException if a database access error occurs.
     */
    public void deleteBook(int bookId) throws SQLException {
        String query = "DELETE FROM book WHERE ID = ?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookId);
            ps.executeUpdate();
        }
    }

    /**
     * Checks if the book with the specified ID is available.
     *
     * @param bookId the unique identifier of the book to check availability for
     * @return true if the book is available, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isBookAvailable(int bookId) throws SQLException {
        String query = "SELECT * FROM book WHERE id = ?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("available");
        }
    }

}
