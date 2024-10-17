package dao;

import model.Book;
import model.Loan;
import model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ReservationDAO provides methods to manage book reservations in the library system.
 * These methods include adding a reservation, checking if a loan exists for a book,
 * deleting a reservation, retrieving the next reservation, checking if a user
 * has a reservation for a particular book, and getting all reservations.
 */
public class ReservationDAO {
    /**
     * Establishes a connection to the database using the provided JDBC URL, user, and password.
     *
     * @return Connection object representing an active connection to the database
     * @throws SQLException if a database access error occurs or the URL is null
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Adds a book to the user's reservation list in the database.
     * If the book is currently on loan, the reservation date is set
     * to one week after the loan's due date. Otherwise, the reservation
     * date is set to the current date.
     *
     * @param user the user who wants to reserve the book
     * @param book the book to be reserved
     * @throws SQLException if a database access error occurs
     */
    public void addBookToReservation(User user, Book book) throws SQLException {
        String query = "INSERT INTO reservations (user_id, book_id, reservation_date) VALUES (?, ?, ?)";
        LocalDate localDate = isLoanExist(book);
        LocalDate dateForReservation;

        if (localDate == null) {
            dateForReservation = LocalDate.now();
        } else
            dateForReservation = localDate.plusDays(7);

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBookId());
            ps.setString(3, dateForReservation.toString());
            ps.executeUpdate();
        }

    }

    /**
     * Checks whether there are any existing loans for the specified book.
     * Retrieves all reservation dates for the book from the database,
     * and returns the most recent reservation date.
     *
     * @param book the book to check for existing loans
     * @return the most recent reservation date if loans exist; null otherwise
     * @throws SQLException if a database access error occurs
     */
    public LocalDate isLoanExist(Book book) throws SQLException {
        List<LocalDate> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE book_id=?";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, book.getBookId());
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                LocalDate checkDate = LocalDate.parse(resultSet.getString("reservation_date"));
                reservations.add(checkDate);

            }
        }
        Collections.sort(reservations);
        if (reservations.isEmpty())
            return null;
        else
            return reservations.getLast();
    }

    /**
     * Deletes a reservation from the database based on the user ID, book ID, and reservation date.
     *
     * @param userId the unique identifier of the user
     * @param bookId the unique identifier of the book
     * @param reservationDate the date on which the reservation was made
     * @throws SQLException if a database access error occurs
     */
    public void deleteReservation(int userId, int bookId, LocalDate reservationDate) throws SQLException {
        String query = "DELETE FROM reservations WHERE user_id=? AND book_id=? AND reservation_date=?";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setString(3, reservationDate.toString());
            ps.executeUpdate();
        }
    }

    /**
     * Processes the next reservation for the specified book. Retrieves the
     * oldest reservation from the reservations table, creates a loan for
     * the user who made the reservation, and then deletes the reservation.
     *
     * @param book the book for which the next reservation is to be processed
     * @throws SQLException if a database access error occurs
     */
    public void getNextReservation(Book book) throws SQLException {

        System.out.println("Klasa ReservationDAO metoda getNextReservation id book = " + book.getBookId());
        LoansDAO loansDAO = new LoansDAO();
        String query = "SELECT * FROM reservations WHERE book_id = ? ORDER BY reservation_date ASC LIMIT 1;";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, book.getBookId());
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                LocalDate reservationDate = LocalDate.parse(resultSet.getString("reservation_date"));
                loansDAO.loanForOnlyId(userId, bookId, reservationDate);
                deleteReservation(userId, bookId, reservationDate);
            }
        }
    }

    /**
     * Checks if the specified user has a reservation for the specified book.
     *
     * @param user the user to check for a reservation
     * @param book the book to check for a reservation
     * @return true if the user has a reservation for the book, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public boolean isHaveReservation(User user, Book book) throws SQLException {
        String query = "SELECT * FROM reservations WHERE user_id = ? AND book_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBookId());
            ResultSet resultSet = ps.executeQuery();

            return resultSet.next();
        }
    }

    /**
     * Retrieves all reservations from the database and returns them as a list of Loan objects.
     *
     * @return a list of Loan objects representing all reservations in the database
     * @throws SQLException if a database access error occurs
     */
    public List<Loan> getAllReservations() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        String query = "SELECT * FROM reservations ORDER BY ID";

        try (Connection conn = getConnection();
        PreparedStatement ps = conn.prepareStatement(query)) {
            ps.executeQuery();
            ResultSet resultSet = ps.getResultSet();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                LocalDate reservationDate = LocalDate.parse(resultSet.getString("reservation_date"));

                Loan loan = new Loan(id, userId, bookId, reservationDate);
                loans.add(loan);
            }
        }
        return loans;
    }

}
