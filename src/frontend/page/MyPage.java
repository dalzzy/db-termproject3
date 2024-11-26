package frontend.page;

import backend.db.DatabaseManager;
import backend.follow.FollowDAO;
import backend.follow.FollowService;
import backend.user.UserDTO;
import backend.user.UserService;
import frontend.component.NavBar;
import frontend.component.EditProfileButton;
import frontend.component.ChangePwdModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyPage extends JFrame {

    private UserService userService;
    private FollowService followService;
    private int userId;

    public MyPage(int userId) {
        this.userService = new UserService(DatabaseManager.getInstance().getConnection());
        this.followService = new FollowService(new FollowDAO(DatabaseManager.getInstance().getConnection()));
        this.userId = userId;

        // 프레임 설정
        setTitle("Profile Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar(this, userId);
        add(navBar, BorderLayout.WEST);

        // 프로필 콘텐츠 영역
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.BLACK);

        // 유저 정보 가져오기
        Optional<UserDTO> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();

            // 상단 배경
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(30, 39, 50));
            headerPanel.setBounds(200, 0, 800, 120);
            profilePanel.add(headerPanel);

            // 프로필 이미지
            JLabel profileImage = new JLabel();
            profileImage.setBounds(250, 120, 110, 120);
            profileImage.setIcon(resizeIcon(new ImageIcon("src/frontend/assets/profileImg.png"), 100, 100));
            profilePanel.add(profileImage);

            // 사용자 이름
            JLabel usernameLabel = new JLabel(user.getName());
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
            usernameLabel.setBounds(250, 230, 300, 30);
            profilePanel.add(usernameLabel);

            // Follower / Following 정보
            int followingCount = followService.getFollowingCount(userId);
            int followerCount = followService.getFollowerCount(userId);

            JLabel followingLabel = new JLabel(followingCount + " Following");
            followingLabel.setForeground(Color.GRAY);
            followingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            followingLabel.setBounds(250, 290, 150, 20);
            profilePanel.add(followingLabel);

            JLabel followersLabel = new JLabel(followerCount + " Followers");
            followersLabel.setForeground(Color.GRAY);
            followersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            followersLabel.setBounds(400, 290, 150, 20);
            profilePanel.add(followersLabel);

            // Following 및 Followers 클릭 이벤트 추가
            addHoverAndClickEvents(followingLabel, "FollowingPage");
            addHoverAndClickEvents(followersLabel, "FollowersPage");

            // "Edit Profile" 버튼
            EditProfileButton editProfileButton = new EditProfileButton(this,userId);
            editProfileButton.setBounds(650, 200, 120, 30);
            profilePanel.add(editProfileButton);

            // 버튼 클릭 이벤트: 모달 표시
            editProfileButton.addActionListener(e -> {
                new ChangePwdModal(this,userId);

            });

            // Posts / Likes Tabs
            JPanel tabPanel = new JPanel();
            tabPanel.setLayout(null);
            tabPanel.setBackground(Color.BLACK);
            tabPanel.setBounds(200, 330, 800, 40);

            JLabel postsTab = new JLabel("Posts");
            postsTab.setForeground(new Color(29, 155, 240));
            postsTab.setFont(new Font("Arial", Font.BOLD, 16));
            postsTab.setBounds(250, 10, 100, 20);
            tabPanel.add(postsTab);

            JLabel likesTab = new JLabel("Likes");
            likesTab.setForeground(Color.GRAY);
            likesTab.setFont(new Font("Arial", Font.PLAIN, 16));
            likesTab.setBounds(350, 10, 100, 20);
            tabPanel.add(likesTab);

            profilePanel.add(tabPanel);

            // Main Content Area (게시물)
            JPanel mainContent = new JPanel();
            mainContent.setBackground(Color.BLACK);
            mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(mainContent);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            scrollPane.setBounds(200, 380, 800, 400);

            // 더미 게시물
            for (int i = 1; i <= 5; i++) {
                JPanel post = new JPanel();
                post.setBackground(Color.DARK_GRAY);
                post.setPreferredSize(new Dimension(760, 100));
                post.setLayout(new FlowLayout(FlowLayout.LEFT));
                JLabel postLabel = new JLabel("Post " + i);
                postLabel.setForeground(Color.WHITE);
                post.add(postLabel);
                mainContent.add(post);
                mainContent.add(Box.createVerticalStrut(10));
            }

            profilePanel.add(scrollPane);
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(profilePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addHoverAndClickEvents(JLabel label, String targetPage) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText("<html><u>" + label.getText() + "</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText(label.getText().replace("<html><u>", "").replace("</u></html>", ""));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (targetPage.equals("FollowersPage")) {
                    List<String> followers = followService.getFollowers(userId)
                            .stream()
                            .map(f -> "User " + f.getUserId())
                            .collect(Collectors.toList());
                    new FollowersPage(followers);
                } else if (targetPage.equals("FollowingPage")) {
                    List<String> following = followService.getFollowedUsers(userId)
                            .stream()
                            .map(f -> "User " + f.getFollowedUserId())
                            .collect(Collectors.toList());
                    new FollowingPage(following);
                }
            }

        });
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void main(String[] args) {
        new MyPage(1);
    }
}
