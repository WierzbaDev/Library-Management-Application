package dao;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO is a data access object class for performing CRUD operations on the User table in the database.
 */
public class UserDAO {
    /**
     * Establishes a connection to the MySQL database configured for the library.
     *
     * @return a Connection object to the library database
     * @throws SQLException if a database access error occurs or the url is null
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "";
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Retrieves a User object from the database based on the provided email.
     *
     * @param email the email address of the user to retrieve
     * @return a User object if a user with the specified email exists; otherwise, returns null
     * @throws SQLException if a database access error occurs
     */
    public User getUserByEmail(String email) throws SQLException {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String password = resultSet.getString("Password");
                String roleString = resultSet.getString("Role");
                User.Role role = User.Role.valueOf(roleString);
                user = new User(id, name,email, password, role);
            }
        }
        return user;

    }

    /**
     * Saves a user to the database.
     *
     * @param user the User object to be saved
     * @return true if the user was successfully saved, false otherwise
     */
    public boolean saveUser(User user) {
        boolean result = false;
        String query = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
        PreparedStatement prStatement = conn.prepareStatement(query)) {

            prStatement.setString(1, user.getName());
            prStatement.setString(2, user.getEmail());
            prStatement.setString(3, user.getPassword());
            prStatement.setString(4, user.getRole().name());

            int rowsAffected = prStatement.executeUpdate();

            if (rowsAffected > 0) {
                result = true;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("lalatublad");
        }
        return result;
    }

    /**
     * Retrieves the role of a user based on their email address.
     *
     * @param email the email address of the user whose role needs to be fetched
     * @return the role of the user as a String if the user exists; otherwise, returns null
     * @throws SQLException if a database access error occurs
     */
    public String getRole(String email) throws SQLException {
        String role = null;
        String query = "SELECT Role FROM user WHERE email = ?";

        try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                role = resultSet.getString("Role");
            }

            return role;
        }
    }

    /**
     * Retrieves the password for a user with the specified email address from the database.
     *
     * @param email the email address of the user whose password is to be retrieved
     * @return the password of the user if found; otherwise, null
     * @throws SQLException if a database access error occurs
     */
    public String getPassword(String email) throws SQLException {
        String password = null;
        String query = "SELECT Password FROM user WHERE email = ?";

        try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                password = resultSet.getString("Password");
            }

            return password;
        }
    }

    /**
     * Retrieves a list of all users from the database.
     *
     * @return a list of User objects representing all users in the database
     * @throws SQLException if a database access error occurs
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<User>();
        String query = "SELECT * FROM user";

        try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String email = resultSet.getString("Email");
                String role = resultSet.getString("Role");

                User user = new User(id, name, email, role);
                users.add(user);
            }
        }

        return users;
    }

    /**
     * Deletes a user from the database based on the provided user ID.
     *
     * @param userId the unique identifier of the user to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM user WHERE ID = ?";

        try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

}
