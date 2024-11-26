package frontend.page;

import backend.db.DatabaseManager;
import backend.follow.FollowDAO;
import backend.follow.FollowService;
import backend.post.PostDTO;
import backend.post.PostService;
import backend.user.UserDTO;
import backend.user.UserService;
import frontend.component.NavBar;
import frontend.component.PostItem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HomePage extends JFrame {
    private JPanel mainContent; // 메인 콘텐츠 영역
    private JTextField postInputField; // 게시물 입력 필드
    private int userId; // 현재 로그인된 사용자 ID
    private PostService postService;
    private FollowService followService;
    private UserService userService;

    public HomePage(int userId) {
        this.userId = userId;

        // 데이터베이스 연결 및 서비스 초기화
        Connection connection = DatabaseManager.getInstance().getConnection();
        this.postService = new PostService(connection);
        this.followService = new FollowService(new FollowDAO(connection));
        this.userService = new UserService(connection);

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

        // 팔로잉 사용자의 게시물 로드
        loadFollowingPosts();

        // 프레임 표시
        setVisible(true);
    }

    private void createPostPanel() {
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.BLACK);
        postPanel.setLayout(null);
        postPanel.setPreferredSize(new Dimension(800, 100));

        // 게시물 입력 필드
        postInputField = new JTextField() {
            private final String placeholder = "What's happening?!";

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC, 16));
                    g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                }
            }
        };
        postInputField.setBackground(Color.DARK_GRAY);
        postInputField.setForeground(Color.WHITE);
        postInputField.setCaretColor(Color.WHITE);
        postInputField.setFont(new Font("Arial", Font.PLAIN, 18));
        postInputField.setBounds(200, 30, 400, 40);
        postInputField.setHorizontalAlignment(JTextField.LEFT);
        postPanel.add(postInputField);

        // 게시 버튼
        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(29, 155, 240));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postButton.setBounds(630, 30, 80, 40);
        postPanel.add(postButton);

        postButton.addActionListener(e -> {
            String content = postInputField.getText().trim();
            if (!content.isEmpty()) {
                addPostToDatabase(content);
                postInputField.setText("");
            }
        });

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        containerPanel.setBackground(Color.BLACK);
        containerPanel.add(postPanel, BorderLayout.NORTH);
        containerPanel.add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        add(containerPanel, BorderLayout.NORTH);
    }

    private void createMainContent() {
        mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadFollowingPosts() {
        try {
            List<Integer> followingIds = followService.getFollowedUsers(userId)
                    .stream()
                    .map(f -> f.getFollowedUserId())
                    .collect(Collectors.toList());

            for (int followingId : followingIds) {
                List<PostDTO> posts = postService.getPostsByUserId(followingId);
                for (PostDTO post : posts) {
                    Optional<UserDTO> userOpt = userService.getUserById(post.getUserId());
                    String username = userOpt.map(UserDTO::getName).orElse("Unknown User");
                    addPost(username, post.getContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPostToDatabase(String content) {
        try {
            PostDTO post = new PostDTO();
            post.setUserId(userId);
            post.setContent(content);
            postService.addPost(post);

            String currentUsername = userService.getUserById(userId)
                    .map(UserDTO::getName)
                    .orElse("Unknown User");

            addPost(currentUsername, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPost(String postUsername, String content) {
        String currentUsername = userService.getUserById(userId)
                .map(UserDTO::getName)
                .orElse("Unknown User");

        PostItem postItem = new PostItem(postUsername, content, currentUsername);
        postItem.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainContent.add(Box.createVerticalStrut(10), 0);
        mainContent.add(postItem, 0);

        mainContent.revalidate();
        mainContent.repaint();
    }

    public static void main(String[] args) {
        new HomePage(1);
    }
}
