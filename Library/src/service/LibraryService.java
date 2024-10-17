package service;

import dao.BookDAO;
import model.Book;

import java.sql.SQLException;

public class LibraryService {
    private final BookDAO bookDAO = new BookDAO();

    public void newBook(Book book) throws SQLException {
        bookDAO.addNewBook(book);
    }
}
