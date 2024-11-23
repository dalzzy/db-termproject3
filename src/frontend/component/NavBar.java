package frontend.component;

import javax.swing.*;
import java.awt.*;

public class NavBar extends JPanel {
    public NavBar() {
        setPreferredSize(new Dimension(200, 0));
        setBackground(Color.BLACK);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));

        // X 로고 추가
        JLabel logo = createLogo();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        add(Box.createVerticalStrut(30)); // 로고 아래 여백


        JButton homeButton = createMenuButton("Home");
        add(homeButton);

        add(Box.createVerticalStrut(15)); // 버튼 간격

        // "Profile" 버튼
        JButton profileButton = createMenuButton("Profile");
        add(profileButton);

        add(Box.createVerticalGlue()); // 남은 공간 아래로 밀기
    }

    // 로고 생성 메서드
    private JLabel createLogo() {
        // assets 폴더에 있는 로고 파일 경로
        String logoPath = "/frontend/assets/XLogo.png";

        // 이미지 로드
        ImageIcon logoIcon = new ImageIcon(logoPath);
        Image logoImage = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // 크기 조정
        return new JLabel(new ImageIcon(logoImage)); // JLabel로 반환
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
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.decode("#e9e9e9")); // 호버 시 배경색 설정
                button.setForeground(Color.BLACK); // 텍스트 색 변경
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.BLACK); // 원래 배경색으로 복귀
                button.setForeground(Color.WHITE); // 텍스트 색 복귀
            }
        });

        return button;
    }
}
