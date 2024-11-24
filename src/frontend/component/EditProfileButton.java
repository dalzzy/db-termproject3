package frontend.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileButton extends JButton {

    public EditProfileButton(JFrame parent) {
        super("Edit Profile"); // 버튼 텍스트 설정
        initializeButton(parent);
    }

    private void initializeButton(JFrame parent) {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBounds(750, 120, 100, 30);

        // 버튼 클릭 시 ChangePwdModal 호출
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePwdModal modal = new ChangePwdModal(parent);
                modal.setVisible(true); // 모달 표시
            }
        });
    }
}
