package frontend.page;

import backend.db.DatabaseManager;  // Backend DatabaseManager for connection
import backend.user.UserDAO;  // Backend UserDAO for user-related queries
import java.util.Optional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JFrame {

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

                try {
                    if (authenticateUser(email, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful! Redirecting to homepage...");
                        dispose(); // Close the login window
                        new HomePage(1); // Open homepage or redirect
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid email or password. Please try again.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error connecting to the database.");
                    ex.printStackTrace();
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

    /**
     * 사용자 인증 함수 (이메일, 비밀번호를 이용해 로그인)
     * @param email 사용자가 입력한 이메일
     * @param password 사용자가 입력한 비밀번호
     * @return 인증 성공 여부
     */
    private boolean authenticateUser(String email, String password) throws SQLException {
        // Get Database connection from DatabaseManager
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            // UserDAO 인스턴스 생성 with the connection
            UserDAO userDAO = new UserDAO(conn);

            // 로그인 시도 (Returns userId if successful)
            Optional<Integer> userId = userDAO.loginUser(email, password);
            return userId.isPresent();  // 사용자 인증 성공 여부 반환
        }
    }

    public static void main(String[] args) {
        new LoginPage();  // Launch the Login Page
    }
}
