package frontend.page;

import backend.db.DatabaseManager;
import backend.user.UserDTO;
import backend.user.UserService;
import frontend.component.NavBar;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MyPage extends JFrame {

    private UserService userService; // UserService 인스턴스
    private int userId; // 조회할 유저 ID

    public MyPage(int userId) {
        // DatabaseManager를 통해 데이터베이스 연결 가져오기
        this.userService = new UserService(DatabaseManager.getInstance().getConnection());
        this.userId = userId;

        // 프레임 속성 설정
        setTitle("Profile Page");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // NavBar
        NavBar navBar = new NavBar(this,userId);
        add(navBar, BorderLayout.WEST);

        // 프로필 콘텐츠 영역
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBackground(Color.BLACK);

        // 유저 정보를 조회하여 표시
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

            // 사용자 이메일 (아이디 대체)
            JLabel userIdLabel = new JLabel(user.getEmail());
            userIdLabel.setForeground(Color.GRAY);
            userIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            userIdLabel.setBounds(250, 260, 300, 20);
            profilePanel.add(userIdLabel);

            // Follower / Following 정보 (목데이터)
            JLabel followingLabel = new JLabel("10 Following"); // TODO: 실제 데이터로 교체
            followingLabel.setForeground(Color.GRAY);
            followingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            followingLabel.setBounds(250, 290, 150, 20);
            profilePanel.add(followingLabel);

            JLabel followersLabel = new JLabel("5 Followers"); // TODO: 실제 데이터로 교체
            followersLabel.setForeground(Color.GRAY);
            followersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            followersLabel.setBounds(400, 290, 150, 20);
            profilePanel.add(followersLabel);
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

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
        // 예시: 1번 유저 프로필 조회
        new MyPage(1);
    }
}
