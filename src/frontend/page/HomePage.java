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
    private int userId; // 현재 로그인된 사용자 ID

    public HomePage(int userId) {
        this.userId = userId;

        // 프레임 속성 설정
        setTitle("Home Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar(this, userId);
        add(navBar, BorderLayout.WEST);

        // 게시물 작성 영역 생성 및 추가
        createPostPanel();

        // 메인 콘텐츠 영역 생성 및 추가
        createMainContent();

        // 프레임 표시
        setVisible(true);
    }

    // 게시물 작성 영역 생성 메서드
    private void createPostPanel() {
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
        postInputField.setBounds(200, 30, 400, 40); // 입력 필드 위치 및 크기 설정
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
                    addPost("User" + userId, content); // 게시물 추가
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
    }

    // 메인 콘텐츠 영역 생성 메서드
    private void createMainContent() {
        mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS)); // 세로 방향 레이아웃

        JScrollPane scrollPane = new JScrollPane(mainContent); // 스크롤 추가
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 설정
        add(scrollPane, BorderLayout.CENTER);

        // 더미 데이터 추가
        addPost("User1", "Hi ");
        addPost("User2", "Hello ~~~~");
        addPost("User3", "user3's content ");
        addPost("User4", "user4's content");
    }

    // 게시물 추가 메서드
    private void addPost(String username, String content) {
        PostItem postItem = new PostItem(username, content);
        postItem.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContent.add(Box.createVerticalStrut(10), 0);
        mainContent.add(postItem, 0);

        mainContent.revalidate(); // 레이아웃 갱신
        mainContent.repaint(); // 화면 갱신
    }

    public static void main(String[] args) {
        // 테스트용: userId 1로 페이지 실행
        new HomePage(1);
    }
}
