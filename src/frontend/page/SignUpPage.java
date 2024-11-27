package frontend.page;

import backend.db.DatabaseManager;  // Importing DatabaseManager to get the connection
import backend.user.UserDAO;        // Importing UserDAO to handle user operations
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUpPage extends JFrame {

    public SignUpPage() {
        setTitle("Signup Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new GridLayout(4, 2));

        // Components
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        // Add components
        add(emailLabel);
        add(emailField);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(signupButton);
        add(backButton);

        // Signup button action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                try {
                    if (registerUser(email, username, password)) {
                        JOptionPane.showMessageDialog(null, "Signup successful! Redirecting to login...");
                        dispose(); // Close the current window
                        new LoginPage(); // Open Login page
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Could not sign up. Please try again.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error connecting to the database.");
                    ex.printStackTrace();
                }
            }
        });

        // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new LoginPage(); // Open Login page
            }
        });

        setVisible(true);
    }

    // Register the user in the database using UserDAO
    private boolean registerUser(String email, String username, String password) throws SQLException {
        boolean isRegistered = false;

        // Establish a database connection using DatabaseManager
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            // Create UserDAO instance
            UserDAO userDAO = new UserDAO(conn);

            // Register user using the UserDAO's registerUser method
            isRegistered = userDAO.registerUser(username, email, password, "1990-01-01", "M"); // Replace with proper birthdate and gender
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error during registration.");
        }

        return isRegistered;
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
