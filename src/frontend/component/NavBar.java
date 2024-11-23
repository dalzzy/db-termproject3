package frontend.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavBar extends JPanel {
    public NavBar() {
        setPreferredSize(new Dimension(300, 0));
        setBackground(Color.BLACK);


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 상단 여백 추가
        add(Box.createVerticalStrut(20));

        // "X" 로고
        JLabel logo = new JLabel("X", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 30));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        add(Box.createVerticalStrut(30)); // 로고 아래 여백

        // "Home" 버튼
        JButton homeButton = createMenuButton("Home");
        add(homeButton);

        add(Box.createVerticalStrut(15)); // 버튼 간격

        // "Profile" 버튼
        JButton profileButton = createMenuButton("Profile");
        add(profileButton);

        add(Box.createVerticalGlue()); // 남은 공간 아래로 밀기
    }

    // 메뉴 버튼 생성 메서드
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(150, 40));

        // MouseListener 추가
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode("#e9e9e9"));
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }
}
