package frontend.page;

import javax.swing.*;
import frontend.component.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Basic frame settings
        setTitle("X");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Set layout manager to null for custom positioning

        // Main panel to hold the components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        add(mainPanel);

        // Logo setup
        ImageIcon logoIcon = new ImageIcon("src/frontend/assets/XLogo.png");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH))); // Adjust size
        logoLabel.setBounds((getWidth() - 200) / 2, 100, 200, 200); // Center the logo
        mainPanel.add(logoLabel);

        // Sign Up button setup
        Button signUpButton = new Button("Sign Up", new Color(29, 155, 240), Color.WHITE);
        signUpButton.setBounds((getWidth() - 150) / 2, 350, 150, 50); // Center the button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new SignUpPage(); // Open the SignUpPage
            }
        });
        mainPanel.add(signUpButton);

        // Login button setup
        Button loginButton = new Button("Login", Color.BLACK, Color.WHITE);
        loginButton.setBounds((getWidth() - 150) / 2, 420, 150, 50); // Center the button below the SignUp button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new LoginPage(); // Open the LoginPage
            }
        });
        mainPanel.add(loginButton);

        // Frame settings
        setVisible(true);
        setLocationRelativeTo(null); // Center the window on the screen
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
