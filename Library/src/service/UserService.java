package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

/**
 * The UserService class provides methods to manage user operations
 * such as registration.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user if the email is not already associated with an existing user.
     *
     * @param name the name of the user
     * @param email the email address of the user
     * @param password the password for the user's account
     * @param role the role assigned to the user
     * @return true if the user was successfully registered, false if a user with the given email already exists
     * @throws SQLException if a database access error occurs or SQL statements fail
     */
    public boolean registerUser(String name, String email, String password, User.Role role) throws SQLException {

        if (userDAO.getUserByEmail(email) != null) {
            return false;
        } else {

            User user = new User(name, email, password, role);

            userDAO.saveUser(user);
            return true;
        }
    }
}
