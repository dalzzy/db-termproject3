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

        JLabel headerLabel = new JLabel("Followers", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBackground(new Color(30, 39, 50));
        headerLabel.setOpaque(true);
        headerLabel.setPreferredSize(new Dimension(400, 50));
        add(headerLabel, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.BLACK);

        for (String user : followersList) {
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

        userPanel.add(usernameLabel);
        return userPanel;
    }
}
