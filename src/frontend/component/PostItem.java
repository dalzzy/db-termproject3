package frontend.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostItem extends JPanel {
    private JPanel replyPanel;

    public PostItem(String username, String content) {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(600, 150));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel userLabel = new JLabel(username);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(userLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(Color.DARK_GRAY);
        contentArea.setForeground(Color.WHITE);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setEditable(false);
        add(contentArea, BorderLayout.CENTER);

        JButton replyButton = new JButton("Reply");
        replyButton.setBackground(new Color(29, 155, 240));
        replyButton.setForeground(Color.WHITE);
        replyButton.setFocusPainted(false);
        replyButton.setBorderPainted(false);
        replyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        replyButton.setPreferredSize(new Dimension(80, 30));

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());
        wrapperPanel.setBackground(Color.DARK_GRAY);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.DARK_GRAY);
        buttonPanel.add(replyButton);
        wrapperPanel.add(buttonPanel, BorderLayout.NORTH);

        replyPanel = new JPanel();
        replyPanel.setLayout(new BoxLayout(replyPanel, BoxLayout.Y_AXIS));
        replyPanel.setBackground(Color.BLACK);
        wrapperPanel.add(replyPanel, BorderLayout.CENTER);

        replyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField replyField = new JTextField();
                replyField.setBackground(Color.DARK_GRAY);
                replyField.setForeground(Color.WHITE);
                replyField.setCaretColor(Color.WHITE);
                replyField.setFont(new Font("Arial", Font.PLAIN, 14));
                replyField.setMaximumSize(new Dimension(500, 30));

                JButton submitReplyButton = new JButton("Submit");
                submitReplyButton.setBackground(new Color(29, 155, 240));
                submitReplyButton.setForeground(Color.WHITE);
                submitReplyButton.setFocusPainted(false);
                submitReplyButton.setBorderPainted(false);
                submitReplyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                submitReplyButton.setMaximumSize(new Dimension(80, 30));

                submitReplyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String replyText = replyField.getText().trim();
                        if (!replyText.isEmpty()) {
                            JLabel replyLabel = new JLabel(username + ": " + replyText);
                            replyLabel.setForeground(Color.LIGHT_GRAY);
                            replyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                            replyPanel.add(replyLabel);
                            replyPanel.add(Box.createVerticalStrut(5));
                            replyPanel.revalidate();
                            replyPanel.repaint();
                            replyField.setText("");
                        }
                    }
                });

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
                inputPanel.setBackground(Color.BLACK);
                inputPanel.add(replyField);
                inputPanel.add(Box.createHorizontalStrut(10));
                inputPanel.add(submitReplyButton);

                replyPanel.add(inputPanel);
                replyPanel.revalidate();
                replyPanel.repaint();
            }
        });

        add(wrapperPanel, BorderLayout.SOUTH);
    }
}
