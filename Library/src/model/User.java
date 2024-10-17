package model;

/**
 * The User class represents a user in the system with unique attributes and roles.
 *
 * It provides constructors for creating users with or without ID,
 * and with either a Role enum or a string representation of the role.
 */
public class User {
    /**
     * Constructs a new User with the specified name, email, password, and role.
     *
     * @param name the name of the user
     * @param email the email address of the user
     * @param password the password for the user's account
     * @param role the role assigned to the user
     */
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructs a new User with the specified id, name, email, password, and role.
     *
     * @param id the unique identifier for the user
     * @param name the name of the user
     * @param email the email address of the user
     * @param password the password for the user's account
     * @param role the role assigned to the user
     */
    public User(int id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructs a new User with the specified id, name, email, and string representation of the role.
     *
     * @param id the unique identifier for the user
     * @param name the name of the user
     * @param email the email address of the user
     * @param stringRole the string representation of the role assigned to the user
     */
    public User(int id, String name, String email, String stringRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.stringRole = stringRole;
    }

    /**
     * Represents roles that a user can have within the system.
     *
     * The available roles are:
     * - STUDENT: A standard user with permissions limited to student-related actions.
     * - ADMIN: A user with administrative privileges.
     */
    public enum Role {
        STUDENT, ADMIN
    }

    /**
     * Retrieves the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the role assigned to the user.
     *
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the password for the user's account.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the string representation of the role assigned to the user.
     *
     * @return the string representation of the user's role
     */
    public String getStringRole() {
        return stringRole;
    }

    /**
     * Retrieves the unique identifier for the user.
     *
     * @return the unique identifier for the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the role assigned to the user.
     *
     * @param role the role to assign to the user
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string containing the name, email, and role of the user
     */
    @Override
    public String toString() {
        return "User [name=" + name + ", email=" + email + ", role=" + role + "]";
    }

    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private String stringRole;
}
