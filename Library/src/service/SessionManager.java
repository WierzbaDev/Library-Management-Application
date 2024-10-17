package service;

import model.User;

/**
 * The SessionManager class manages the user's session within the application.
 *
 * It provides methods to log in, log out, check login status, and verify
 * if the logged-in user has administrative privileges.
 */
public class SessionManager {
    private static User loggedInUser;

    /**
     * Logs in a user by setting the current logged-in user.
     *
     * @param user the user to be logged in
     */
    public static void loginUser(User user) {
        loggedInUser = user;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the currently logged-in user, or null if no user is logged in
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Logs out the currently logged-in user.
     *
     * This method sets the loggedInUser variable to null, effectively
     * logging out the user and invalidating the current session.
     */
    public static void logout() {
        loggedInUser = null;
    }

    /**
     * Checks whether a user is currently logged into the session.
     *
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    /**
     * Checks if the currently logged-in user has administrative privileges.
     *
     * @return true if the logged-in user is an admin, false otherwise
     */
    public static boolean isAdmin() {
        return loggedInUser != null && loggedInUser.getRole() == User.Role.ADMIN;
    }
}
