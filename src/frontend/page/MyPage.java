package frontend.page;

import frontend.component.NavBar;
import frontend.component.EditProfileButton;

import javax.swing.*;
import java.awt.*;

public class MyPage extends JFrame {

    public MyPage() {
        // 프레임 속성 설정
        setTitle("Profile Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar(this);
        add(navBar, BorderLayout.WEST);

        // 프로필 콘텐츠 영역
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null); // 절대 위치
        profilePanel.setBackground(Color.BLACK);

        // 상단 배경
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.setBounds(200, 0, 800, 150);
        profilePanel.add(headerPanel);

        // 프로필 이미지
        JLabel profileImage = new JLabel();
        profileImage.setBounds(250, 100, 100, 100); // 프로필 이미지 위치
        profileImage.setIcon(resizeIcon(new ImageIcon("src/frontend/assets/default_profile.png"), 100, 100));
        profilePanel.add(profileImage);

        // 사용자 이름
        JLabel usernameLabel = new JLabel("Dahyeon");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        usernameLabel.setBounds(370, 120, 200, 30); // 이름 위치
        profilePanel.add(usernameLabel);

        // 사용자 아이디
        JLabel userIdLabel = new JLabel("@dalzzy");
        userIdLabel.setForeground(Color.GRAY);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userIdLabel.setBounds(370, 150, 200, 20); // 아이디 위치
        profilePanel.add(userIdLabel);

        // Follower / Following 정보
        JLabel followersLabel = new JLabel("30 Followers");
        followersLabel.setForeground(Color.WHITE);
        followersLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        followersLabel.setBounds(370, 180, 100, 20);
        profilePanel.add(followersLabel);

        JLabel followingLabel = new JLabel("10 Following");
        followingLabel.setForeground(Color.WHITE);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        followingLabel.setBounds(470, 180, 100, 20);
        profilePanel.add(followingLabel);

        // "Edit Profile" 버튼
        EditProfileButton editProfileButton = new EditProfileButton(this); // 현재 프레임 전달
        profilePanel.add(editProfileButton);



        // Posts / Likes Tabs
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(null);
        tabPanel.setBackground(Color.BLACK);
        tabPanel.setBounds(200, 220, 800, 40);

        JLabel postsTab = new JLabel("Posts");
        postsTab.setForeground(Color.CYAN); // 활성화된 탭
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
        scrollPane.setBounds(200, 260, 800, 500);

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

        profilePanel.add(scrollPane);

        add(profilePanel, BorderLayout.CENTER);

        // 프레임 표시
        setVisible(true);
    }

    // 이미지 리사이즈 메서드
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public static void main(String[] args) {
        new MyPage();
    }
}
