package frontend.page;

import backend.db.DatabaseManager;
import backend.follow.FollowDAO;
import backend.follow.FollowService;
import backend.user.UserDTO;
import backend.user.UserService;
import frontend.component.NavBar;
import frontend.component.EditProfileButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class MyPage extends JFrame {

    private UserService userService;
    private FollowService followService;
    private int userId;

    public MyPage(int userId) {
        this.userId = userId;
        this.userService = new UserService(DatabaseManager.getInstance().getConnection());
        this.followService = new FollowService(new FollowDAO(DatabaseManager.getInstance().getConnection()));

        // 프레임 설정
        setTitle("Profile Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar 추가
        NavBar navBar = new NavBar(this, userId);
        add(navBar, BorderLayout.WEST);

        // 프로필 콘텐츠 생성
        JPanel profilePanel = createProfileContent();
        add(profilePanel, BorderLayout.CENTER);

        // 프레임 표시
        setVisible(true);
    }

    private JPanel createProfileContent() {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.BLACK);

        // 사용자 정보 가져오기
        Optional<UserDTO> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            UserDTO user = userOptional.get();

            // 상단 배경 패널
            JPanel headerPanel = new JPanel();
            headerPanel.setBackground(new Color(30, 39, 50));
            headerPanel.setBounds(200, 0, 800, 120);
            profilePanel.add(headerPanel);

            // 프로필 이미지
            JLabel profileImage = new JLabel();
            profileImage.setBounds(250, 120, 110, 120);
            profileImage.setIcon(resizeIcon(new ImageIcon("src/frontend/assets/profileImg.png"), 100, 100));
            profilePanel.add(profileImage);

            // 사용자 이름 표시
            JLabel usernameLabel = new JLabel(user.getName());
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
            usernameLabel.setBounds(250, 230, 300, 30);
            profilePanel.add(usernameLabel);

            // 팔로우 / 팔로잉 정보
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

            // 팔로우 / 팔로잉 클릭 이벤트 추가
            addHoverAndClickEvents(followingLabel, "FollowingPage");
            addHoverAndClickEvents(followersLabel, "FollowersPage");

            // Edit Profile 버튼 추가
            EditProfileButton editProfileButton = new EditProfileButton(this, userId);
            editProfileButton.setBounds(650, 200, 120, 30);
            profilePanel.add(editProfileButton);

            // Posts/Likes 탭 추가
            JPanel tabPanel = createTabPanel();
            tabPanel.setBounds(200, 330, 800, 40);
            profilePanel.add(tabPanel);

            // 게시물 섹션 추가
            JScrollPane postsScrollPane = createPostsSection();
            postsScrollPane.setBounds(200, 380, 800, 400);
            profilePanel.add(postsScrollPane);

        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return profilePanel;
    }

    private JPanel createTabPanel() {
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(null);
        tabPanel.setBackground(Color.BLACK);

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

        return tabPanel;
    }

    private JScrollPane createPostsSection() {
        JPanel mainContent = new JPanel();
        mainContent.setBackground(Color.BLACK);
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        // 더미 게시물 추가
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

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    private void addHoverAndClickEvents(JLabel label, String targetPage) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                label.setText("<html><u>" + label.getText() + "</u></html>");
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                label.setText(label.getText().replace("<html><u>", "").replace("</u></html>", ""));
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (targetPage.equals("FollowingPage")) {
                    List<String> followedUsers = followService.getFollowedUserNames(userId);
                    new FollowingPage(userId, followedUsers, followService, userService);
                } else if (targetPage.equals("FollowersPage")) {
                    List<String> followers = followService.getFollowerNames(userId);
                    new FollowersPage(followers);
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
        new MyPage(1); // 테스트용 userId
    }
}
