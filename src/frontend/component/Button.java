package frontend.component;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, Color bgColor, Color textColor) {
        super(text); // 버튼 텍스트 설정
        setBackground(bgColor); // 배경색 설정
        setForeground(textColor); // 텍스트 색상 설정
        setFont(new Font("Arial", Font.BOLD, 14)); // 글꼴 설정
        setFocusPainted(false); // 포커스 표시 제거
        setContentAreaFilled(true); // 버튼 영역 채우기
        setOpaque(true); // 불투명도 설정
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스를 올렸을 때 손 모양 커서
    }
}
