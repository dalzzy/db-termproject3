package frontend.component;

import frontend.page.HomePage;
import frontend.page.MyPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavBar extends JPanel {
    private JFrame parentFrame;
    private int userId;

    public NavBar(JFrame parentFrame, int userId) {
        this.parentFrame = parentFrame;
        this.userId = userId;

        // NavBar 속성 설정
        setPreferredSize(new Dimension(200, 0));
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 로고 위치 조정 (최상단에 배치)
        add(Box.createVerticalStrut(20)); // NavBar 상단 여백
        JLabel logo = createLogo();
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(logo);

        // 로고 아래 여백
        add(Box.createVerticalStrut(30));

        // Home 버튼 추가
        JButton homeButton = createMenuButton("Home", () -> {
            parentFrame.dispose();
            new HomePage(userId);
        });
        add(homeButton);

        // 버튼 간격
        add(Box.createVerticalStrut(15));

        // Profile 버튼 추가
        JButton profileButton = createMenuButton("Profile", () -> {
            parentFrame.dispose();
            new MyPage(userId);
        });
        add(profileButton);

        // NavBar 하단 여백 추가
        add(Box.createVerticalGlue());
    }

    // 로고 생성 메서드
    private JLabel createLogo() {
        String logoPath = "src/frontend/assets/XLogo.png";

        ImageIcon logoIcon = new ImageIcon(logoPath);
        Image logoImage = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH); // 크기 조정
        return new JLabel(new ImageIcon(logoImage)); // JLabel로 반환
    }

    // 메뉴 버튼 생성 메서드
    private JButton createMenuButton(String text, Runnable onClickAction) {
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

        // 버튼 클릭 이벤트 추가
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClickAction.run(); // 전달된 동작 실행
            }
        });

        return button;
    }
}
