package dao;

import model.Book;
import model.Loan;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoansDAO class provides data access methods for managing the loans of books in a library.
 * It includes methods for loaning books, returning books, checking availability status,
 * and retrieving loan information.
 */
public class LoansDAO {
    /**
     * Establishes and returns a connection to the database.
     *
     **/
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Loans a book to a user by recording the loan in the database and changing the book's availability status.
     *
     * @param user the user who is borrowing the book
     * @param book the book to be loaned to the user
     * @throws SQLException if a database access error occurs
     */
    public void loanBook(User user, Book book) throws SQLException {
        int condition = 0;
        LocalDate today = LocalDate.now();
        String query = "Insert into loans (user_id, book_id, loan_date) values (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBookId());
            ps.setString(3, today.toString());

            int rowAffected = ps.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Loaned Book");
                changeAvailableStatus(book, condition);
            } else
                System.out.println("you did not loan Book");

        }
    }

    /**
     * Checks the availability status of a given book in the library database.
     *
     * @param book the book whose availability status is to be checked
     * @return true if the book is available, false otherwise
     * @throws SQLException if a database access*/
    public boolean checkAvailableStatus(Book book) throws SQLException {
        boolean available = true;
        String query = "Select * from book where ID = ?";

        try (Connection conn = getConnection();
        PreparedStatement prStatement = conn.prepareStatement(query)) {
            prStatement.setInt(1, book.getBookId());
            ResultSet resultSet = prStatement.executeQuery();

            if (resultSet.next()) {
                available = resultSet.getBoolean("available");

            }
        }
        return available;
    }

    /**
     * Updates the return date of a borrowed book for a specific user in the database
     * and changes the book's availability status.
     *
     * @param user the user returning the book
     * @param book the book being returned
     * @throws SQLException if a database access error occurs
     */
    public void returnBook(User user, Book book) throws SQLException {
        int condition = 1;
        LocalDate today = LocalDate.now();
        String query = "UPDATE loans SET return_date = ? WHERE user_id = ? AND book_id = ?";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, today.toString());
            ps.setInt(2, user.getId());
            ps.setInt(3, book.getBookId());
            int rowAffected = ps.executeUpdate();
            System.out.println(rowAffected);
        }
        changeAvailableStatus(book, condition);
    }

    /**
     * Changes the availability status of a given book based on its current loan status in the database.
     *
     * @param book the book whose availability status is to be changed
     * @param condition an integer representing a condition that may influence the availability status
     * @throws SQLException if a database access error occurs
     */
    public void changeAvailableStatus(Book book, int condition) throws SQLException {
        int checkCondition = condition;
        LocalDate today = LocalDate.now();
        String querySelect = "SELECT COUNT(*) AS return_date FROM loans WHERE return_date IS NULL AND book_id = ?;";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(querySelect)) {
            ps.setInt(1, book.getBookId());
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            int count = resultSet.getInt("return_date");
            resultSet.close();

            if (count == 0) {
                checkCondition = 1;
            } else
                checkCondition = 0;
        }

        if (checkCondition == 0) {
            String query = "UPDATE book SET available = 0, available_from = ? WHERE ID = ?";

            try (Connection conn = getConnection();
            PreparedStatement prStatement = conn.prepareStatement(query)) {
                prStatement.setString(1, today.toString());
                prStatement.setInt(2, book.getBookId());
                prStatement.executeUpdate();
            }
        }
        else if (checkCondition == 1) {
            String query = "UPDATE book SET available = 1, available_from = NULL WHERE ID = ?";

            try (Connection conn = getConnection();
                 PreparedStatement prStatement = conn.prepareStatement(query)) {
                prStatement.setInt(1, book.getBookId());
                prStatement.executeUpdate();
            }
        }

    }

    /**
     * Processes and loans a book to the next user in the reservation list based on the given reservation date.
     *
     * @param localDate the date on which the reservation was made
     * @throws SQLException if a database access error occurs
     */
    public void loanForNextUser(LocalDate localDate) throws SQLException {
        int userId = 0;
        int bookId = 0;
        LocalDate localDate1;
        String query = "Select * from reservations where reservation_date = ?";
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, localDate.toString());
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
                bookId = resultSet.getInt("book_id");
                localDate1 = LocalDate.parse(resultSet.getString("reservation_date"));
                loanForOnlyId(userId, bookId, localDate1);
                resultSet.close();
            }

        }

    }

    /**
     * Records a loan of a book to a user by inserting the loan details into the database.
     *
     * @param userId the ID of the user borrowing the book
     * @param bookId the ID of the book being borrowed
     * @param reservationDate the date the book was reserved
     * @throws SQLException if a database access error occurs
     */
    public void loanForOnlyId(int userId, int bookId, LocalDate reservationDate) throws SQLException {
        String query = "INSERT INTO loans (user_id, book_id, loan_date) values (?, ?, ?)";
        System.out.println("Klasa LoansDAO, metoda loanForOnlyId book id = " + bookId);
        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setString(3, reservationDate.toString());
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves all loan records associated with a specific user from the database.
     *
     * @param user The user whose loan records are to be retrieved.
     * @return A list of Loan objects representing the loans made by the user.
     * @throws SQLException If a database access error occurs.
     */
    public List<Loan> getAllLoans(User user) throws SQLException{
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loans WHERE user_id = ?";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                LocalDate loanDate = LocalDate.parse(resultSet.getString("loan_date"));
                String returnDate = resultSet.getString("return_date");

                Loan loan = new Loan(id, userId, bookId, loanDate, returnDate);
                loans.add(loan);
            }
        }
        return loans;
    }

    /**
     * Checks whether a specific user has loaned a specific book and has not returned it yet.
     *
     * @param user the user whose loan status is to be checked
     * @param book the book to be checked
     * @return true if the user has loaned the book and not yet returned it, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean hasLoanedBook(User user, Book book) throws SQLException {
        String query = "SELECT * FROM loans WHERE user_id = ? AND book_id = ? AND return_date IS NULL";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBookId());
            ResultSet resultSet = ps.executeQuery();

            return resultSet.next(); // Zwraca true, jeśli użytkownik wypożyczył książkę
        }
    }

    /**
     * Retrieves all loan records from the database.
     *
     * @return A list of Loan objects representing all loans in the database.
     * @throws SQLException If a database access error occurs.
     */
    public List<Loan> getAllUserLoans() throws SQLException{
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM loans";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                LocalDate loanDate = LocalDate.parse(resultSet.getString("loan_date"));
                String returnDate = resultSet.getString("return_date");

                Loan loan = new Loan(id, userId, bookId, loanDate, returnDate);
                loans.add(loan);
            }
        }
        return loans;
    }

}
