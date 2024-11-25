package frontend.page;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FollowersPage extends JFrame {

    public FollowersPage(List<String> followersList) {
        // 프레임 설정
        setTitle("Followers");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 헤더
        JLabel headerLabel = new JLabel("Followers", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBackground(new Color(30, 39, 50));
        headerLabel.setOpaque(true);
        headerLabel.setPreferredSize(new Dimension(400, 50));
        add(headerLabel, BorderLayout.NORTH);

        // 팔로워 목록 패널
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.BLACK);

        // 팔로워 사용자 목록 추가
        if (followersList.isEmpty()) {
            // 팔로워 목록이 비어있을 때 표시
            JLabel emptyLabel = new JLabel("No Followers");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(Box.createVerticalGlue());
            listPanel.add(emptyLabel);
            listPanel.add(Box.createVerticalGlue());
        } else {
            // 팔로워 목록이 있을 경우 사용자 추가
            for (String user : followersList) {
                JPanel userPanel = createUserPanel(user);
                listPanel.add(userPanel);
                listPanel.add(Box.createVerticalStrut(10)); // 사용자 간격 추가
            }
        }

        // 스크롤 패널로 감싸기
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 프레임 표시
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 사용자 패널 생성 메서드
    private JPanel createUserPanel(String user) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        userPanel.setBackground(Color.DARK_GRAY);
        userPanel.setPreferredSize(new Dimension(380, 50));
        userPanel.setMaximumSize(new Dimension(380, 50));

        // 사용자 이름
        JLabel usernameLabel = new JLabel(user);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);

        userPanel.add(usernameLabel);
        return userPanel;
    }

    public static void main(String[] args) {
        // 예제 데이터: 목데이터로 팔로워 사용자 목록 전달
        List<String> followersList = List.of("Follower1", "Follower2", "Follower3", "Follower4", "Follower5");
        new FollowersPage(followersList);
    }
}
