package frontend.page;

import java.util.Optional;
import backend.user.UserDAO; // Import the UserDAO class
import backend.db.DatabaseManager; // Import the DatabaseManager class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {

    public LoginPage() {
        // Change all fonts to Arial
        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14));
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("Arial", Font.PLAIN, 14));

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // email label and text field
        JLabel userLabel = new JLabel("Email:");
        userLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        JTextField userText = new JTextField(20);
        userText.setBackground(Color.DARK_GRAY);
        userText.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(userText, gbc);

        // Password label and text field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBackground(Color.DARK_GRAY);
        passwordText.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordText, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 35));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText().trim();
                String password = new String(passwordText.getPassword()).trim();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter both email and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try (Connection conn = DatabaseManager.getInstance().getConnection()) {
                        UserDAO userDAO = new UserDAO(conn); // Create an instance of UserDAO
                        Optional<Integer> userId = userDAO.loginUser(username, password);
                        if (userId.isPresent()) {
                            JOptionPane.showMessageDialog(null, "Login successful! Redirecting to the main page...");
                            dispose(); // Close the login window
                            new HomePage(userId.get()); // Redirect to main page
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);

        // Add button panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        // Add main panel to frame
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Frame settings
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
