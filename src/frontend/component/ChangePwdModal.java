package frontend.component;

import backend.user.ProfileService;
import backend.db.DatabaseManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ChangePwdModal extends JDialog {
    private int userId;
    private ProfileService profileService;

    public ChangePwdModal(JFrame parent, int userId) {
        super(parent, "Change Password", true); // 부모 프레임과 모달 설정
        this.userId = userId;
        Connection connection = DatabaseManager.getInstance().getConnection();
        this.profileService = new ProfileService(connection);
        initializeModal();
    }

    private void initializeModal() {
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(getParent()); // 부모 창 중심에 모달 표시

        // 제목
        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 비밀번호 변경 폼
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel currentPasswordLabel = new JLabel("Current Password:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");

        JPasswordField currentPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        formPanel.add(currentPasswordLabel);
        formPanel.add(currentPasswordField);
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        add(formPanel, BorderLayout.CENTER);

        // 하단 버튼
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        // 저장 버튼 클릭 이벤트
        saveButton.addActionListener(e -> {
            String currentPassword = new String(currentPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = profileService.changePassword(userId, currentPassword, newPassword);
            if (success) {
                JOptionPane.showMessageDialog(this, "Password changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // 모달 닫기
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change password. Check your current password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 취소 버튼 클릭 이벤트
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
