package frontend.page;

import frontend.component.NavBar;
import frontend.component.EditProfileButton;
import frontend.component.ChangePwdModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

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

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 39, 50));
        headerPanel.setBounds(200, 0, 800, 120);
        profilePanel.add(headerPanel);

        // 프로필 이미지
        JLabel profileImage = new JLabel();
        profileImage.setBounds(250, 120, 110, 120); // 프로필 이미지 위치 조정
        profileImage.setIcon(resizeIcon(new ImageIcon("src/frontend/assets/profileImg.png"), 100, 100));
        profilePanel.add(profileImage);

        // 사용자 이름
        JLabel usernameLabel = new JLabel("Dahyeon");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        usernameLabel.setBounds(250, 230, 300, 30); // 이름 위치 조정
        profilePanel.add(usernameLabel);

        // 사용자 아이디
        JLabel userIdLabel = new JLabel("@dalzzy");
        userIdLabel.setForeground(Color.GRAY);
        userIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userIdLabel.setBounds(250, 260, 300, 20); // 아이디 위치 조정
        profilePanel.add(userIdLabel);

        // Follower / Following 정보
        JLabel followingLabel = new JLabel("10 Following");
        followingLabel.setForeground(Color.GRAY);
        followingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        followingLabel.setBounds(250, 290, 150, 20); // Following 위치 조정
        profilePanel.add(followingLabel);

        JLabel followersLabel = new JLabel("5 Followers");
        followersLabel.setForeground(Color.GRAY);
        followersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        followersLabel.setBounds(400, 290, 150, 20); // Followers 위치 조정
        profilePanel.add(followersLabel);

        // Following 및 Followers 호버 및 클릭 이벤트 추가
        addHoverAndClickEvents(followingLabel, "FollowingPage");
        addHoverAndClickEvents(followersLabel, "FollowersPage");

        // "Edit Profile" 버튼
        EditProfileButton editProfileButton = new EditProfileButton(this); // 현재 프레임 전달
        editProfileButton.setBounds(650, 200, 120, 30); // 버튼 위치 조정
        profilePanel.add(editProfileButton);

        // 버튼 클릭 이벤트: 모달 표시
        editProfileButton.addActionListener(e -> {
            new ChangePwdModal(this); // 모달 표시
        });

        // Posts / Likes Tabs
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(null);
        tabPanel.setBackground(Color.BLACK);
        tabPanel.setBounds(200, 330, 800, 40); // 탭 위치를 아래로 이동

        JLabel postsTab = new JLabel("Posts");
        postsTab.setForeground(new Color(29, 155, 240)); // 활성화된 탭 색상
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
        scrollPane.setBounds(200, 380, 800, 400); // 게시물 영역 위치 조정

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

        add(profilePanel, BorderLayout.CENTER);

        // 프레임 표시
        setVisible(true);
    }

    // 호버 및 클릭 이벤트 추가 메서드
    private void addHoverAndClickEvents(JLabel label, String targetPage) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setText("<html><u>" + label.getText() + "</u></html>"); // 밑줄 추가
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setText(label.getText().replace("<html><u>", "").replace("</u></html>", "")); // 밑줄 제거
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (targetPage.equals("FollowingPage")) {
                    // 목데이터: 팔로잉 사용자 목록
                    List<String> followingList = Arrays.asList("User1", "User2", "User3", "User4", "User5");
                    new FollowingPage(followingList); // FollowingPage로 이동
                }
                else if (targetPage.equals("FollowersPage")) {
                    // 목데이터: 팔로워 사용자 목록
                    List<String> followersList = Arrays.asList("Follower1", "Follower2", "Follower3", "Follower4", "Follower5");
                    new FollowersPage(followersList); // FollowersPage로 이동
                }
            }
        });
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
