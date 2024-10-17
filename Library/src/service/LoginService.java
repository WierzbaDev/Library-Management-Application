package service;

import model.User;
import dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

/**
 * The LoginService class provides functionality to handle user login operations.
 *
 * This service interacts with the UserDAO to retrieve user information
 * from the database.
 */
public class LoginService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Logs a user in based on the provided email address.
     *
     * @param email the email address of the user attempting to log in
     * @return the User object if the email is found, otherwise returns null
     * @throws SQLException if any SQL errors occur during the execution
     */
    public User login(String email) throws SQLException {
        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            return user;
        }

        return null;
    }
}
