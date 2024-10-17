package ui;

import dao.*;
import model.*;
import org.mindrot.jbcrypt.BCrypt;
import service.Reservation;
import service.SessionManager;
import service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainApp extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel logPanel;
    private JPanel titlePanel;
    private JLabel emailLabel;
    private JTextField emailField;
    private JLabel passwordLabel;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel titleLabel;
    private JPanel registerPanel;
    private JPanel registerTitlePanel;
    private JPanel regPanel;
    private JLabel registerEmailLabel;
    private JTextField registerEmailField;
    private JLabel repeatPass;
    private JPasswordField passwordField2;
    private JLabel registerPasswordLabel;
    private JPasswordField passwordField3;
    private JButton backToLoginButton;
    private JButton registerSubmit;
    private JRadioButton studentRadioButton;
    private JRadioButton adminRadioButton;
    private JLabel registerStatusLabel;
    private JPanel bookPanel;
    private JLabel loginStatusLabel;
    private JPanel studentPanel;
    private JPanel bookMainPanel;
    private JPanel adminPanel;
    private JLabel welcomeLabel;
    private JTable tableBooks;
    private JPanel buttonPanel;
    private JButton loanbutton;
    private JPanel welcomePanel;
    private JButton logoutButton;
    private JTextField nameField;
    private JLabel nameLabel;
    private JPanel loansPanel;
    private JButton myLoansButton;
    private JTable loansTable;
    private JPanel reservationPanel;
    private JTable reservationTable;
    private JTable booksTableAdmin;
    private JPanel buttonAdminPanel;
    private JButton userButtonAdmin;
    private JButton reservationsAdminButton;
    private JPanel welcomeAdminPanel;
    private JButton logoutAdminButton;
    private JButton booksAdminButton;
    private JButton loansAdminButton;
    private JPanel loansAdminPanel;
    private JTable adminLoansTable;
    private JButton adminLoansBackButton;
    private JPanel reservationsAdminPanel;
    private JTable reservationsAdminTable;
    private JButton reservationsAdminBackButton;
    private JPanel usersAdminPanel;
    private JTable userAdminTable;
    private JButton usersBackButton;
    private JButton addUserButton;
    private JPanel addUserPanel;
    private JTextField addUserNameField;
    private JLabel addUserName;
    private JTextField addUserEmailField;
    private JLabel addUserEmail;
    private JLabel addUserPass;
    private JPasswordField addUserPassField;
    private JPasswordField addUserPassField2;
    private JLabel addUserRepPass;
    private JRadioButton studentRadioUserPanel;
    private JRadioButton adminRadioUserPanel;
    private JButton submitUserButton;
    private JButton addUserBackToUsers;
    private JLabel addUserStatus;
    private JPanel booksAdminPanel;
    private JButton deleteAdminBook;
    private JLabel titleBookLabel;
    private JTextField titleField;
    private JTextField textField2;
    private JButton submitButton;
    private JLabel statusBookLabel;
    private JLabel authorFIeld;
    private JButton backToMainAdmin;
    private JButton deleteUserButton;
    private CardLayout cardLayout;
    private DefaultTableModel model;
    private DefaultTableModel modelLoans;
    private DefaultTableModel modelReservations;
    private DefaultTableModel modelAdminBooks;
    private DefaultTableModel modelAdminLoans;
    private DefaultTableModel modelReservationsAdmin;
    private DefaultTableModel modelAdminUsers;
    private ButtonGroup buttonGroup;
    private JButton backToBookButton;
    private JPanel buttonLoanPanel;
    private JButton returnBookButton;
    private JButton reservationButton;
    private JButton reservationBackToBookButton;
    private JPanel reservationButtonPanel;


    public MainApp() {
        //super("Library Management System");
        this.setTitle("Library Management System");
        this.setBounds(1280,640,680,680);
        //this.setBounds(1280,640,680,300);
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");
        mainPanel.add(studentPanel, "book");
        mainPanel.add(loansPanel, "loans");
        mainPanel.add(reservationPanel, "reservation");
        mainPanel.add(adminPanel, "admin");
        mainPanel.add(loansAdminPanel, "loansAdmin");
        mainPanel.add(reservationsAdminPanel, "reservationsAdmin");
        mainPanel.add(usersAdminPanel, "usersAdmin");
        mainPanel.add(addUserPanel, "addUser");
        mainPanel.add(booksAdminPanel, "addBook");
        getContentPane().add(mainPanel);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(studentRadioButton);
        buttonGroup.add(adminRadioButton);
    }

    public void initComponents() {
        registerButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "register");
            }
        });

        registerSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameField.getText().trim().isEmpty() && !registerEmailField.getText().trim().isEmpty() && !Arrays.toString(passwordField1.getPassword()).trim().isEmpty() && !Arrays.toString(passwordField2.getPassword()).trim().isEmpty() && studentRadioButton.isSelected() || adminRadioButton.isSelected()) {

                    if (nameField.getText().length() > 2 && passwordField2.getText().length() > 5) {

                        if (Arrays.equals(passwordField2.getPassword(), passwordField3.getPassword())) {
                            User.Role role = null;
                            if (studentRadioButton.isSelected()) {
                                role = User.Role.STUDENT;
                            } else if (adminRadioButton.isSelected()) {
                                role = User.Role.ADMIN;
                            }
                            String enteredPassword = new String(passwordField2.getPassword());
                            String hashedPassword = BCrypt.hashpw(enteredPassword, BCrypt.gensalt());

                            UserService userService = new UserService();
                            boolean result = false;
                            try {
                                result = userService.registerUser(nameField.getText(), registerEmailField.getText(), hashedPassword, role);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            if (result) {
                                registerStatusLabel.setText("Successfully registered, please login");
                                registerStatusLabel.setForeground(Color.GREEN);

                                nameField.setText("");
                                registerEmailField.setText("");
                                passwordField1.setText("");
                                passwordField2.setText("");
                                passwordField3.setText("");
                                studentRadioButton.setSelected(false);
                                adminRadioButton.setSelected(false);
                            } else {
                                registerStatusLabel.setText("This email address is already in use");
                                registerStatusLabel.setForeground(Color.RED);
                            }
                        } else {
                            registerStatusLabel.setText("Passwords do not match");
                            registerStatusLabel.setForeground(Color.RED);
                        }

                    } else {
                        registerStatusLabel.setText("Your name and password must have at least 5 characters");
                        registerStatusLabel.setForeground(Color.RED);
                    }

                }
                else {
                    registerStatusLabel.setText("Please fill all the fields");
                    registerStatusLabel.setForeground(Color.RED);
                }

            }
        });

        backToLoginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "login");
            }
        });

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    UserDAO userDAO = new UserDAO();

                    String enteredPassword = new String(passwordField1.getPassword());
                    User user = userDAO.getUserByEmail(emailField.getText());
                    if (user != null) {
                        String storedPassword = user.getPassword();

                        if (BCrypt.checkpw(enteredPassword, storedPassword)) {
                            SessionManager.loginUser(user);
                            if (!SessionManager.isAdmin()) {
                                cardLayout.show(mainPanel, "book");
                            } else {
                                cardLayout.show(mainPanel, "admin");
                                refreshTableBooks(modelAdminBooks);
                            }
                    }

                        passwordField1.setText("");
                        emailField.setText("");
                        loginStatusLabel.setText("");
                    } else {
                        loginStatusLabel.setForeground(Color.RED);
                        loginStatusLabel.setText("Wrong email or password");
                    }
                    refreshTableBooks(model);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainApp.this, "Error while downloading books from database", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //table
        String[] columnNames = {"ID", "Title", "Author", "Available", "Available From"};

        model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableBooks.setModel(model);

        //Loans panel
            //table Loan
        String[] columnNamesLoan = {"ID", "Book ID", "Loan Date", "Return Date"};
        modelLoans = new DefaultTableModel(columnNamesLoan, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        loansPanel.setLayout(new GridLayout(2,1));
        backToBookButton = new JButton("Back to Books");
        returnBookButton = new JButton("Return Book");
        loansTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buttonLoanPanel = new JPanel(new FlowLayout());
        buttonLoanPanel.add(backToBookButton);
        buttonLoanPanel.add(returnBookButton);



        loansTable.setModel(modelLoans);
        JScrollPane scrollPaneloans = new JScrollPane(loansTable);
        loansPanel.add(scrollPaneloans, BorderLayout.NORTH);
        loansPanel.add(buttonLoanPanel);

        //reservation Panel

        reservationPanel.setLayout(new GridLayout(2,1));
        reservationButtonPanel = new JPanel(new FlowLayout());
        reservationPanel.add(reservationButtonPanel);
        reservationBackToBookButton = new JButton("Back to Books");
        reservationButtonPanel.add(reservationBackToBookButton, BorderLayout.SOUTH);

        //reservation table
        String[] columnNamesReservations = new String[] {"ID", "User Id", "Book Id", "Reservation date"};
        modelReservations = new DefaultTableModel(columnNamesReservations, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reservationTable.setModel(modelReservations);
        JScrollPane jScrollPaneReservations = new JScrollPane(reservationTable);
        reservationPanel.add(jScrollPaneReservations, BorderLayout.NORTH);
        // Panel dla studenta - użycie BorderLayout
        studentPanel.setLayout(new BorderLayout());

        //adminPanel
        buttonAdminPanel = new JPanel(new FlowLayout());
        modelAdminBooks = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        adminPanel = new JPanel(new GridLayout(3,1));

        booksTableAdmin.setModel(modelAdminBooks);
        JScrollPane JScrollPaneAdminBooks = new JScrollPane(booksTableAdmin);
        buttonAdminPanel = new JPanel(new FlowLayout());

        buttonAdminPanel.add(userButtonAdmin);
        buttonAdminPanel.add(deleteAdminBook);
        buttonAdminPanel.add(booksAdminButton);
        buttonAdminPanel.add(reservationsAdminButton);
        buttonAdminPanel.add(loansAdminButton);

        welcomeAdminPanel = new JPanel(new FlowLayout());

        JLabel welcomeAdminLabel = new JLabel("Welcome Admin");

        welcomeAdminPanel.add(welcomeAdminLabel);
        welcomeAdminPanel.add(logoutAdminButton);

        adminPanel.add(welcomeAdminPanel);
        adminPanel.add(JScrollPaneAdminBooks);
        adminPanel.add(buttonAdminPanel);
            //loansPanelAdmin

        modelAdminLoans = new DefaultTableModel(columnNamesLoan, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        loansAdminPanel = new JPanel();
        adminLoansTable.setModel(modelAdminLoans);
        JScrollPane JScrollPaneAdminLoans = new JScrollPane(adminLoansTable);
        loansAdminPanel.add(JScrollPaneAdminLoans);
        loansAdminPanel.add(adminLoansBackButton);


            //reservationsAdminPanel
        reservationsAdminPanel = new JPanel();
        reservationsAdminPanel.add(reservationsAdminBackButton);
        modelReservationsAdmin = new DefaultTableModel(columnNamesReservations, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        reservationsAdminTable.setModel(modelReservationsAdmin);
        JScrollPane JScrollPaneAdminReservations = new JScrollPane(reservationsAdminTable);
        reservationsAdminPanel.add(JScrollPaneAdminReservations);


            //usersAdminPanel
        usersAdminPanel = new JPanel();
        usersAdminPanel.add(usersBackButton);
        usersAdminPanel.add(addUserButton);
        usersAdminPanel.add(deleteUserButton);


        String[] columnNamesUser = new  String[] {"ID", "Name", "Email", "Role"};
        modelAdminUsers = new DefaultTableModel(columnNamesUser, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userAdminTable.setModel(modelAdminUsers);
        JScrollPane JScrollPaneAdminUsers = new JScrollPane(userAdminTable);
        usersAdminPanel.add(JScrollPaneAdminUsers);
        //userAdminTable.setPreferredSize(new Dimension(100, 100));

        //welcome panel
        welcomeLabel.setText("Welcome to Library Management System");
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(logoutButton);
        studentPanel.add(welcomePanel, BorderLayout.NORTH);

        // Etykieta powitalna nad tabelą
        JScrollPane scrollPane = new JScrollPane(tableBooks);
        studentPanel.add(scrollPane, BorderLayout.CENTER);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(loanbutton);
        buttonPanel.add(myLoansButton);
        reservationButton = new JButton("Reservations");
        buttonPanel.add(reservationButton);

        studentPanel.add(buttonPanel, BorderLayout.SOUTH);

        loanbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableBooks.getSelectedRow();

                if (selectedRow != -1) {
                    int bookId = Integer.parseInt(tableBooks.getValueAt(selectedRow, 0).toString());
                    String title = tableBooks.getValueAt(selectedRow, 1).toString();
                    String author = tableBooks.getValueAt(selectedRow, 2).toString();
                    String availableText = tableBooks.getValueAt(selectedRow, 3).toString();
                    boolean available = availableText.equals("Available");
                    Book book = new Book(bookId, title, author, available);
                    Reservation reservation = new Reservation();
                    try {
                        reservation.tryLoanBook(SessionManager.getLoggedInUser(), book);
                        BookDAO bookDAO = new BookDAO();
                        List<Book> books = bookDAO.getAllBooks();
                        populateTableWithBooks(books, model);
                        //books - model

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        myLoansButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                LoansDAO loansDAO = new LoansDAO();
                try {
                    List<Loan> loans = loansDAO.getAllLoans(SessionManager.getLoggedInUser());
                    populateTableWithLoans(loans, modelLoans);
                    // loans - modelLoans
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                cardLayout.show(mainPanel, "loans");
            }
        });

        logoutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.logout();
                cardLayout.show(mainPanel, "login");
            }
        });

        backToBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "book");
            }
        });

        returnBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = loansTable.getSelectedRow();
                Book book = null;
                if (selectedRow != -1) {
                    int bookId = Integer.parseInt(loansTable.getValueAt(selectedRow, 1).toString());
                    book = new Book(bookId);
                }
                Reservation reservation = new Reservation();
                if (book != null) {
                    try {
                        reservation.returnBook(SessionManager.getLoggedInUser(), book);
                        LoansDAO loansDAO = new LoansDAO();
                        List<Loan> loansList = loansDAO.getAllLoans(SessionManager.getLoggedInUser());
                        populateTableWithLoans(loansList, modelLoans);
                        //model - loansModel

                        BookDAO bookDAO = new BookDAO();
                        List<Book> books = bookDAO.getAllBooks();
                        populateTableWithBooks(books, model);
                        //model - books
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationDAO reservationDAO = new ReservationDAO();
                try {
                    List<Loan> loans = reservationDAO.getAllReservations();
                    populateTableWithReservations(loans, modelReservations);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                cardLayout.show(mainPanel, "reservation");
            }
        });

        reservationBackToBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "book");
            }
        });

        logoutAdminButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.logout();
                cardLayout.show(mainPanel, "login");
            }
        });

        loansAdminButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "loansAdmin");
                LoansDAO loansDAO = new LoansDAO();

                try {
                    List<Loan> loanList = loansDAO.getAllUserLoans();
                    populateTableWithLoans(loanList, modelAdminLoans);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        adminLoansBackButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });

        reservationsAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationDAO reservationDAO = new ReservationDAO();
                try {
                    List<Loan> loans = reservationDAO.getAllReservations();
                    populateTableWithReservations(loans, modelReservationsAdmin);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                cardLayout.show(mainPanel, "reservationsAdmin");
            }
        });

        reservationsAdminBackButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });

        userButtonAdmin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    refreshTableUsers(modelAdminUsers);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                cardLayout.show(mainPanel, "usersAdmin");
            }
        });

        usersBackButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "admin");
            }
        });

        addUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "addUser");
            }
        });

        addUserBackToUsers.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                UserDAO userDAO = new UserDAO();
                try {
                    List<User> users = userDAO.getAllUsers();
                    populateTableWithUsers(users, modelAdminUsers);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                cardLayout.show(mainPanel, "usersAdmin");
            }
        });

        submitUserButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!addUserNameField.getText().trim().isEmpty() && !addUserEmailField.getText().trim().isEmpty() && !Arrays.toString(addUserPassField.getPassword()).trim().isEmpty() && !Arrays.toString(addUserPassField2.getPassword()).trim().isEmpty() && studentRadioUserPanel.isSelected() || adminRadioUserPanel.isSelected()) {

                    if (addUserNameField.getText().length() > 2 && addUserPassField.getText().length() > 5) {

                        if (Arrays.equals(addUserPassField.getPassword(), addUserPassField2.getPassword())) {
                            User.Role role = null;
                            if (studentRadioUserPanel.isSelected()) {
                                role = User.Role.STUDENT;
                            } else if (adminRadioUserPanel.isSelected()) {
                                role = User.Role.ADMIN;
                            }
                            String enteredPassword = new String(addUserPassField.getPassword());
                            String hashedPassword = BCrypt.hashpw(enteredPassword, BCrypt.gensalt());

                            UserService userService = new UserService();
                            boolean result = false;
                            try {
                                result = userService.registerUser(addUserNameField.getText(), addUserEmailField.getText(), hashedPassword, role);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(mainPanel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            if (result) {
                                addUserStatus.setText("Successfully registered");
                                addUserStatus.setForeground(Color.GREEN);
                                addUserNameField.setText("");
                                addUserEmailField.setText("");
                                addUserPassField.setText("");
                                addUserPassField2.setText("");
                                addUserNameField.requestFocus();
                                addUserNameField.selectAll();
                                addUserNameField.requestFocusInWindow();
                                addUserNameField.setCaretPosition(0);
                                addUserNameField.setEditable(true);
                            } else {
                                addUserStatus.setText("This email address is already in use");
                                addUserStatus.setForeground(Color.RED);
                            }
                        } else {
                            addUserStatus.setText("Passwords do not match");
                            addUserStatus.setForeground(Color.RED);
                        }
                    } else {
                        addUserStatus.setText("Your name and password must have at least 5 characters");
                        addUserStatus.setForeground(Color.RED);
                    }

                }
                else {
                    addUserStatus.setText("Please fill all the fields");
                    addUserStatus.setForeground(Color.RED);
                }
            }
        });

        booksAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "addBook");
            }
        });

        backToMainAdmin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    refreshTableBooks(modelAdminBooks);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                statusBookLabel.setText("");
                cardLayout.show(mainPanel, "admin");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BookDAO bookDAO = new BookDAO();
                Book book = null;
                if (!titleField.getText().trim().isEmpty() && !textField2.getText().trim().isEmpty()) {

                    try {
                        if (!bookDAO.isTheSameTitle(titleField.getText())) {
                            book = new Book(titleField.getText(), textField2.getText(), true);
                            bookDAO.addNewBook(book);

                            statusBookLabel.setForeground(Color.GREEN);
                            statusBookLabel.setText("The book was added successfully");
                            titleField.setText("");
                            textField2.setText("");
                        } else {
                            statusBookLabel.setForeground(Color.RED);
                            statusBookLabel.setText("The book with them title already exists");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    statusBookLabel.setForeground(Color.RED);
                    statusBookLabel.setText("Please fill all the fields");
                }
            }
        });

        deleteAdminBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = booksTableAdmin.getSelectedRow();
                BookDAO bookDAO = new BookDAO();

                if (selectedRow != -1) {
                    int bookId = Integer.parseInt(booksTableAdmin.getValueAt(selectedRow, 0).toString());

                    try {
                        if (bookDAO.isBookAvailable(bookId)) {
                            int adminOption = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this book?");

                            if (adminOption == JOptionPane.YES_OPTION) {
                                bookDAO.deleteBook(bookId);
                                refreshTableBooks(modelAdminBooks);
                            }
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "You cannot delete a book that is currently loaned", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userAdminTable.getSelectedRow();
                UserDAO userDAO = new UserDAO();
                if (selectedRow != -1) {
                    int userId = Integer.parseInt(userAdminTable.getValueAt(selectedRow, 0).toString());

                    int optionAdmin = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to delete this user?");
                    if (optionAdmin == JOptionPane.YES_OPTION) {
                        try {
                            if (SessionManager.getLoggedInUser().getId() != userId) {
                                userDAO.deleteUser(userId);
                                refreshTableUsers(modelAdminUsers);
                            } else {
                                JOptionPane.showMessageDialog(mainPanel, "You cannot delete yourself", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        });

    }

    public void populateTableWithBooks(List<Book> books, DefaultTableModel model) {
        model.setRowCount(0);

        for (Book book: books) {
            String available = book.isAvailable() ? "Available" : "Not Available";
            String availableFrom = book.isAvailable() ? "N/A" : book.getAvailable_from().toString();

            model.addRow(new Object[]{
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                available,
                availableFrom
            });
        }
    }

    public void populateTableWithLoans(List<Loan> loans, DefaultTableModel model) {
        model.setRowCount(0);

        for (Loan loan : loans) {
            model.addRow(new Object[]{
                    loan.getId(),
                    loan.getBookId(),
                    loan.getLoanDate(),
                    loan.getReturnDateDatabase()
            });
        }
    }
    //change this method and others
    public void populateTableWithReservations(List<Loan> loans, DefaultTableModel model) {
        model.setRowCount(0);

        for (Loan loan : loans) {
            model.addRow(new Object[]{
                    loan.getId(),
                    loan.getUserId(),
                    loan.getBookId(),
                    loan.getReservationDate()
            });
        }
    }

    public void populateTableWithUsers(List<User> users, DefaultTableModel model) {
        model.setRowCount(0);

        for (User user: users) {
            model.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getStringRole()
            });
        }
    }

    public void refreshTableBooks(DefaultTableModel model) throws SQLException {
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();
        populateTableWithBooks(books, model);
    }

    public void refreshTableUsers(DefaultTableModel model) throws SQLException {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAllUsers();
        populateTableWithUsers(users, model);
    }

    public static void main(String[] args) {
        new MainApp().setVisible(true);
    }
}
