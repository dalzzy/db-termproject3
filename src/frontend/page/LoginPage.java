package frontend.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginPage extends JFrame {
    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/dbtermproject"; // 데이터베이스 주소
    private final String DB_USER = "ieunju"; // 데이터베이스 사용자 이름
    private final String DB_PASSWORD = "1234"; // 데이터베이스 비밀번호
    private final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC 드라이버 클래스 이름

    public LoginPage() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        // Components
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Go to Signup");

        // Add components
        add(emailLabel);
        add(emailField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(signupButton);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                    return;
                }

                if (authenticateUser(email, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful! Redirecting to homepage...");
                    dispose(); // Close the login window
//                    new HomePage(); // Open homepage
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
                }
            }
        });

        // Signup button action
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new SignUpPage(); // Open Signup page
            }
        });

        setVisible(true);
    }

    // Authenticate user by checking the database
    private boolean authenticateUser(String email, String password) {
        boolean isAuthenticated = false;

        try {
            // Load the database driver
            Class.forName(DB_DRIVER);

            // Connect to the database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                isAuthenticated = rs.next(); // If a record exists, the user is authenticated
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database.");
        }

        return isAuthenticated;
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}