package frontend.page;

import backend.follow.FollowService;
import backend.user.UserService;
import backend.user.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FollowingPage extends JFrame {

    private FollowService followService;
    private UserService userService;
    private int userId; // 현재 로그인된 사용자 ID

    public FollowingPage(int userId, List<String> followingList, FollowService followService, UserService userService) {
        this.userId = userId;
        this.followService = followService;
        this.userService = userService;

        // 프레임 설정
        setTitle("Following");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel headerLabel = new JLabel("Following", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBackground(new Color(30, 39, 50));
        headerLabel.setOpaque(true);
        headerLabel.setPreferredSize(new Dimension(400, 50));
        add(headerLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.BLACK);

        // 전달받은 followingList를 기반으로 사용자 목록 생성
        for (String user : followingList) {
            JPanel userPanel = createUserPanel(user);
            listPanel.add(userPanel);
            listPanel.add(Box.createVerticalStrut(10));
        }

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createUserPanel(String user) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setPreferredSize(new Dimension(380, 50));

        JLabel usernameLabel = new JLabel(user);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);

        JButton unfollowButton = new JButton("Unfollow");
        unfollowButton.setFont(new Font("Arial", Font.PLAIN, 14));
        unfollowButton.setForeground(Color.WHITE);
        unfollowButton.setBackground(new Color(220, 53, 69));
        unfollowButton.setFocusPainted(false);
        unfollowButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 언팔로우 버튼 클릭 이벤트
        unfollowButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to unfollow " + user + "?",
                    "Unfollow Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // 해당 사용자의 ID 가져오기
                    int followedUserId = userService.getUserByName(user)
                            .map(UserDTO::getUserId)
                            .orElseThrow(() -> new RuntimeException("User not found: " + user));
                    boolean success = followService.unfollowUser(userId, followedUserId);

                    if (success) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Unfollowed " + user // 사용자 이름으로 메시지 표시
                        );
                        userPanel.setVisible(false); // UI에서 사용자 제거
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Failed to unfollow " + user
                        );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null,
                            "An error occurred while unfollowing " + user
                    );
                }
            }
        });



        userPanel.add(usernameLabel);
        userPanel.add(unfollowButton);
        return userPanel;
    }

}
