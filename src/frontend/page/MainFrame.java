package frontend.page;

import javax.swing.*;
import frontend.component.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 창 설정
        setTitle("Twitter");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 상단 메뉴바 설정
        JPanel topMenu = new JPanel();
        topMenu.setLayout(null);
        topMenu.setBackground(Color.WHITE);
        topMenu.setBounds(0, 0, getWidth(), 70);

        // 트위터 로고 추가
        JLabel logoLabel = new JLabel(new ImageIcon("src/frontend/assets/XLogo.png"));
        logoLabel.setBounds(20, 10, 50, 50);
        topMenu.add(logoLabel);

        // 메뉴 아이콘 추가
        String[] menuItems = {"Home", "Explore", "Notifications", "Messages"};
        String[] iconPaths = {
                "src/frontend/assets/home_icon.png",
                "src/frontend/assets/explore_icon.png",
                "src/frontend/assets/notification_icon.png",
                "src/frontend/assets/message_icon.png"
        };

        int xOffset = 90;
        for (int i = 0; i < menuItems.length; i++) {
            JLabel menuLabel = new JLabel(menuItems[i], new ImageIcon(iconPaths[i]), JLabel.CENTER);
            menuLabel.setBounds(xOffset, 10, 100, 50);
            xOffset += 120;
            topMenu.add(menuLabel);
        }

        add(topMenu);

        // 회원가입(Sign Up) 버튼
        Button signUpButton = new Button("Sign Up", new Color(29, 155, 240), Color.WHITE);
        signUpButton.setBounds(getWidth() - 220, 10, 100, 40); // 상단 오른쪽
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                new SignUpPage(); // 회원가입 페이지 열기
            }
        });
        topMenu.add(signUpButton);

        // 로그인(Login) 버튼
        Button loginButton = new Button("Login", Color.BLACK, Color.WHITE);
        loginButton.setBounds(getWidth() - 110, 10, 100, 40); // 상단 오른쪽
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                new LoginPage(); // 로그인 페이지 열기
            }
        });
        topMenu.add(loginButton);

        // 트윗 리스트 영역
        JPanel tweetPanel = new JPanel();
        tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));
        tweetPanel.setBackground(new Color(245, 248, 250));
        tweetPanel.setBounds(0, 70, getWidth(), getHeight() - 140);

        JScrollPane scrollPane = new JScrollPane(tweetPanel);
        scrollPane.setBounds(0, 70, getWidth(), getHeight() - 140);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane);

        // 트윗 작성 버튼
        Button tweetButton = new Button("", new Color(29, 155, 240), Color.WHITE);
        tweetButton.setIcon(new ImageIcon("resources/tweet_button_icon.png"));
        tweetButton.setBounds(getWidth() - 80, getHeight() - 80, 60, 60);
        tweetButton.setBorderPainted(false);
        add(tweetButton);

        // 배경색 설정
        getContentPane().setBackground(Color.WHITE);

        // 창 보이기
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
