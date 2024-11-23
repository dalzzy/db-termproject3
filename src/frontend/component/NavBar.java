package frontend.component;

import javax.swing.*;
import java.awt.*;

public class NavBar extends JPanel {
    public NavBar() {
        // 레이아웃 및 배경색 설정
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // 상단 여백 추가
        add(Box.createVerticalStrut(20));

        // "X" 로고 (임시 레이블)
        JLabel logo = new JLabel("X", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        add(Box.createVerticalStrut(30)); // 로고 아래 여백 추가

        // "Home" 버튼
        JButton homeButton = new JButton("Home");
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setBackground(Color.BLACK);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorderPainted(false);
        homeButton.setFont(new Font("Arial", Font.PLAIN, 18));
        homeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeButton.setMaximumSize(new Dimension(150, 40));
        add(homeButton);

        add(Box.createVerticalStrut(15)); // 버튼 간격 추가

        // "Profile" 버튼
        JButton profileButton = new JButton("Profile");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setBackground(Color.BLACK);
        profileButton.setForeground(Color.WHITE);
        profileButton.setFocusPainted(false);
        profileButton.setBorderPainted(false);
        profileButton.setFont(new Font("Arial", Font.PLAIN, 18));
        profileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileButton.setMaximumSize(new Dimension(150, 40));
        add(profileButton);

        add(Box.createVerticalGlue()); // 하단으로 밀어내기
    }
}
