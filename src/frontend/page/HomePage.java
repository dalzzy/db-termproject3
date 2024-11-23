package frontend.page;

import frontend.component.NavBar;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    public HomePage() {
        // 프레임 속성 설정
        setTitle("Home Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar();
        add(navBar, BorderLayout.WEST);

        // 메인 콘텐츠 영역
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(null);


        // post 버튼
        JButton postButton = new JButton("Post");
        postButton.setBounds(50, 100, 100, 40);
        postButton.setBackground(new Color(29, 155, 240));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        mainContent.add(postButton);

        // 메인 콘텐츠를 중앙에 추가
        add(mainContent, BorderLayout.CENTER);

        // 프레임 표시
        setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
