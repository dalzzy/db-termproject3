package frontend.component;

import javax.swing.*;
import java.awt.*;

public class PostItem extends JPanel {
    public PostItem(String username, String content) {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(100, 150)); // 게시물 크기
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 게시물 경계선

        // 사용자 이름
        JLabel userLabel = new JLabel(username);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(userLabel, BorderLayout.NORTH);

        // 게시물 내용
        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(Color.DARK_GRAY);
        contentArea.setForeground(Color.WHITE);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 12));
        contentArea.setEditable(false);
        add(contentArea, BorderLayout.CENTER);
    }
}
