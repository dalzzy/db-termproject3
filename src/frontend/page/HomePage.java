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
        postPanel.setLayout(null); // 절대 위치로 배치
        postPanel.setPreferredSize(new Dimension(800, 100)); // 고정 높이 설정

        // 게시물 입력 필드
        postInputField = new JTextField() {
            private final String placeholder = "What's happening?!"; // Placeholder 텍스트

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) { // 필드가 비어있고 포커스를 잃었을 때만 Placeholder 표시
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY); // Placeholder 색상
                    g2.setFont(getFont().deriveFont(Font.ITALIC, 16));
                    g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                }
            }
        };
        postInputField.setBackground(Color.DARK_GRAY);
        postInputField.setForeground(Color.WHITE);
        postInputField.setCaretColor(Color.WHITE); // 커서 색상
        postInputField.setFont(new Font("Arial", Font.PLAIN, 18));
        postInputField.setBounds(200, 30, 400, 100); // 높이를 40으로 조정
        postInputField.setHorizontalAlignment(JTextField.LEFT); // 텍스트를 왼쪽 정렬
        postPanel.add(postInputField);


        // 게시 버튼
        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(29, 155, 240));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postButton.setBounds(630, 30, 80, 40); // 입력 필드 오른쪽에 배치
        postPanel.add(postButton);

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

        // 게시물 작성 영역을 상단에 추가
        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setBackground(Color.BLACK);
        containerPanel.add(postPanel, BorderLayout.NORTH);
        containerPanel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH); // 간격 추가
        add(containerPanel, BorderLayout.NORTH);

        // 메인 콘텐츠 영역
        mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // 세로 방향 레이아웃
        JScrollPane scrollPane = new JScrollPane(mainContent); // 스크롤 추가
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 설정
        add(scrollPane, BorderLayout.CENTER);

        // 초기 더미 게시물 추가
        mainContent.add(new PostItem("User1", "Hi "));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User2", "Hello ~~~~"));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User3", "user3's content "));
        mainContent.add(Box.createVerticalStrut(10)); // 게시물 간격
        mainContent.add(new PostItem("User4", "user4's content"));

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
