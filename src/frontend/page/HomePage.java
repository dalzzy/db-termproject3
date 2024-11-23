package frontend.page;

import frontend.component.NavBar;
import frontend.component.PostItem;

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


        // post 버튼
//        JButton postButton = new JButton("Post");
//        postButton.setBounds(50, 100, 100, 40);
//        postButton.setBackground(new Color(29, 155, 240));
//        postButton.setForeground(Color.WHITE);
//        postButton.setFocusPainted(false);
//        postButton.setBorderPainted(false);
//        mainContent.add(postButton);

        // 메인 콘텐츠 영역
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // 세로 방향 레이아웃
        JScrollPane scrollPane = new JScrollPane(mainContent); // 스크롤 추가
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 설정
        add(scrollPane, BorderLayout.CENTER);

        // 더미 게시물 추가
        mainContent.add(new PostItem("User1", "This is the first post!"));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User2", "Another post with some content."));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User3", "Yet another post in the feed."));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User4", "This is a longer post to test scrolling functionality in the application."));

        // 프레임 표시
        setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}