package frontend.page;

import backend.user.UserDAO; // Import the UserDAO class
import backend.db.DatabaseManager; // Import the DatabaseManager class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class SignUpPage extends JFrame {

    public SignUpPage() {
        setTitle("Signup Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new GridLayout(6, 2));

        // UI components
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel birthDateLabel = new JLabel("Birth Date (YYYY-MM-DD):");
        JTextField birthDateField = new JTextField();
        JLabel genderLabel = new JLabel("Gender (M/F):");
        JComboBox<String> genderField = new JComboBox<>(new String[]{"M", "F"});
        JButton signupButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Login");

        // Add components to layout
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(birthDateLabel);
        add(birthDateField);
        add(genderLabel);
        add(genderField);
        add(signupButton);
        add(backButton);

        // Sign Up button action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String birthDate = birthDateField.getText().trim();
                String gender = genderField.getSelectedItem().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || birthDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!email.contains("@")) {
                    JOptionPane.showMessageDialog(null, "Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (Connection conn = DatabaseManager.getInstance().getConnection()) {
                    UserDAO userDAO = new UserDAO(conn); // Create an instance of UserDAO

                    if (userDAO.registerUser(name, email, password, birthDate, gender)) {
                        JOptionPane.showMessageDialog(null, "Signup successful! Redirecting to login...");
                        dispose(); // Close the current window
                        new LoginPage(); // Redirect to login page
                    } else {
                        JOptionPane.showMessageDialog(null, "Signup failed. Please try again.", "Signup Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error connecting to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Back to Login button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new LoginPage(); // Redirect to login page
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}
