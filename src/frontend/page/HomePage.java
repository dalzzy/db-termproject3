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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HomePage extends JFrame {
    private JPanel mainContent;
    private JTextField postInputField;
    private int userId;
    private PostService postService;
    private FollowService followService;
    private UserService userService;

    public HomePage(int userId) {
        this.userId = userId;

        Connection connection = DatabaseManager.getInstance().getConnection();
        this.postService = new PostService(connection);
        this.followService = new FollowService(new FollowDAO(connection));
        this.userService = new UserService(connection);

        setTitle("Home Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        NavBar navBar = new NavBar(this, userId);
        add(navBar, BorderLayout.WEST);

        createPostPanel();
        createMainContent();
        loadFollowingPosts();

        setVisible(true);
    }

    private void createPostPanel() {
        JPanel postPanel = new JPanel();
        postPanel.setBackground(Color.BLACK);
        postPanel.setLayout(null);
        postPanel.setPreferredSize(new Dimension(800, 100));

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
        postPanel.add(postInputField);

        JButton postButton = new JButton("Post");
        postButton.setBackground(new Color(29, 155, 240));
        postButton.setForeground(Color.WHITE);
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postButton.setBounds(630, 30, 80, 40);
        postPanel.add(postButton);

        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = postInputField.getText().trim();
                if (!content.isEmpty()) {
                    addPostToDatabase(content);
                    postInputField.setText("");
                }
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
                    String username = userService.getUserById(post.getUserId())
                            .map(UserDTO::getName)
                            .orElse("Unknown User");
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

            String username = userService.getUserById(userId).map(UserDTO::getName).orElse("Unknown User");
            addPost(username, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPost(String username, String content) {
        PostItem postItem = new PostItem(username, content);
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
