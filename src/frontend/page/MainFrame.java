package frontend.page;

import javax.swing.*;
import frontend.component.Button;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        // 창 제목 및 크기 설정
        setTitle("Main Page");
        setSize(1000, 800); // 창 너비 1000, 높이 800
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 버튼 크기
        int buttonWidth = 200;
        int buttonHeight = 50;

        // "Sign Up" 버튼
        Button signUpButton = new Button("Sign Up", new Color(29, 155, 240), Color.WHITE);
        signUpButton.setBounds(
                (getWidth() - buttonWidth) / 2, // X 위치를 창의 가운데로 설정
                (getHeight() / 2) - buttonHeight - 10, // Y 위치를 창의 세로 가운데 위쪽
                buttonWidth,
                buttonHeight
        );
        add(signUpButton);

        // "Login" 버튼
        Button loginButton = new Button("Login", Color.BLACK, Color.WHITE);
        loginButton.setBounds(
                (getWidth() - buttonWidth) / 2, // X 위치를 창의 가운데로 설정
                (getHeight() / 2) + 10, // Y 위치를 창의 세로 가운데 아래쪽
                buttonWidth,
                buttonHeight
        );
        add(loginButton);

        // 배경색 설정
        getContentPane().setBackground(Color.BLACK);

        // 창 보이기
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame(); // 메인 프레임 실행
    }
}
