package frontend.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SignUpPage extends JFrame {
    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/dbtermproject"; // 데이터베이스 주소
    private final String DB_USER = "ieunju"; // 데이터베이스 사용자 이름
    private final String DB_PASSWORD = "1234"; // 데이터베이스 비밀번호
    private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC 드라이버 클래스 이름

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

                if (registerUser(email, username, password)) {
                    JOptionPane.showMessageDialog(null, "Signup successful! Redirecting to login...");
                    dispose(); // Close the current window
                    new LoginPage(); // Open Login page
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Could not sign up. Please try again.");
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

    // Register the user in the database
    private boolean registerUser(String email, String username, String password) {
        boolean isRegistered = false;

        try {
            // Load the database driver
            Class.forName(DB_DRIVER);

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO Users (Email, Username, Password) VALUES (?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, username);
                pstmt.setString(3, password); // Note: Encrypt the password in production

                int rowsInserted = pstmt.executeUpdate();
                isRegistered = rowsInserted > 0; // Check if the insert was successful
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database.");
        }

        return isRegistered;
    }

    public static void main(String[] args) {
        new SignUpPage();
    }
}

