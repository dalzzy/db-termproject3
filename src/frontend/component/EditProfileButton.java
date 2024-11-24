package frontend.component;

import javax.swing.*;
import java.awt.*;

public class EditProfileButton extends JButton {

    public EditProfileButton() {
        super("Edit Profile"); // 버튼 텍스트 설정
        initializeButton();
    }

    private void initializeButton() {
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.WHITE));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBounds(750, 120, 100, 30);
    }
}
