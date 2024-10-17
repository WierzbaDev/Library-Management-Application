package service;

import dao.*;
import model.*;

import java.sql.SQLException;

/**
 * The Reservation class is responsible for managing the process of reserving and returning books for users in a library system.
 */
public class Reservation {
    /**
     * Attempts to loan a book to a user. If the book is available and not already loaned out,
     * it will be loaned to the user. Otherwise, the book will be added to the user's reservation list.
     *
     * @param user the user attempting to loan the book
     * @param book the book the user wants to loan
     * @throws SQLException if any SQL errors occur during the loan or reservation process
     */
    public void tryLoanBook(User user, Book book) throws SQLException {
        ReservationDAO reservationDAO = new ReservationDAO();
        LoansDAO loansDAO = new LoansDAO();
        boolean available = loansDAO.checkAvailableStatus(book);

        if (reservationDAO.isLoanExist(book) == null && available) {
            loansDAO.loanBook(user, book);
            System.out.println("Pomyślne zarezerwowanie książki");
        } else {
            reservationDAO.addBookToReservation(user, book);
            System.out.println("Niestety książka niedostępna ale zostałeś dodany na liste rezerwacji");
        }
    }

    /**
     * Processes the return of a borrowed book by a user.
     * Checks if the user currently has the book on loan, and if so,
     * marks the book as returned and fetches the next reservation, if any.
     *
     * @param user the user returning the book
     * @param book the book being returned
     * @throws SQLException if any SQL error occurs during the process
     */
    public void returnBook(User user, Book book) throws SQLException {
        LoansDAO loansDAO = new LoansDAO();
        ReservationDAO reservationDAO = new ReservationDAO();

        boolean hasLoan = loansDAO.hasLoanedBook(user, book);

        if (hasLoan) {
            loansDAO.returnBook(user, book);
            reservationDAO.getNextReservation(book);
        }
    }




}
