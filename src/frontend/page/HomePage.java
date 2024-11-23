package frontend.page;

import frontend.component.NavBar;
import frontend.component.PostItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JPanel mainContent; // 메인 콘텐츠 영역
    private JTextField postInputField; // 게시물 입력 필드

    public HomePage() {
        // 프레임 속성 설정
        setTitle("Home Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar();
        add(navBar, BorderLayout.WEST);

        // 상단 게시물 작성 영역
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.BLACK);
        postPanel.setLayout(new BorderLayout()); // 상단 중앙 정렬을 위한 BorderLayout 사용
        postPanel.setPreferredSize(new Dimension(800, 100)); // 높이 설정

        // 게시물 입력 필드
        postInputField = new JTextField();
        postInputField.setBackground(Color.DARK_GRAY);
        postInputField.setForeground(Color.WHITE);
        postInputField.setCaretColor(Color.WHITE); // 커서 색상
        postInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        postInputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postPanel.add(postInputField, BorderLayout.CENTER);

        // 게시 버튼
        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(29, 155, 240));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postButton.setPreferredSize(new Dimension(80, 40)); // 버튼 크기 설정

        // 버튼 클릭 이벤트
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = postInputField.getText().trim();
                if (!content.isEmpty()) {
                    addPost("User", content); // 게시물 추가
                    postInputField.setText(""); // 입력 필드 초기화
                }
            }
        });

        // 버튼을 입력 필드 오른쪽에 추가
        postPanel.add(postButton, BorderLayout.EAST);

        // 게시물 작성 영역을 상단에 추가
        add(postPanel, BorderLayout.NORTH);

        // 메인 콘텐츠 영역
        mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // 세로 방향 레이아웃
        JScrollPane scrollPane = new JScrollPane(mainContent); // 스크롤 추가
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 설정
        add(scrollPane, BorderLayout.CENTER);

        // 초기 더미 게시물 추가
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

    // 게시물 추가 메서드
    private void addPost(String username, String content) {
        PostItem postItem = new PostItem(username, content);
        postItem.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainContent.add(postItem);
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.revalidate(); // 레이아웃 갱신
        mainContent.repaint(); // 화면 갱신
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
