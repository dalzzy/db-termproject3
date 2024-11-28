package frontend.page;

import javax.swing.*;
import frontend.component.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 창 설정
        setTitle("X");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 상단 메뉴바 설정
        JPanel topMenu = new JPanel();
        topMenu.setLayout(null);
        topMenu.setBackground(Color.WHITE);
        topMenu.setBounds(0, 0, 1000, 70); // 메뉴바 크기 고정
        add(topMenu); // 상단 메뉴바 추가

        // 트위터 로고 추가
        ImageIcon logoIcon = new ImageIcon("src/frontend/assets/XLogo.png");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH))); // 로고 크기 조정
        logoLabel.setBounds(20, 10, 50, 50);
        topMenu.add(logoLabel);

        // 회원가입(Sign Up) 버튼
        Button signUpButton = new Button("Sign Up", new Color(29, 155, 240), Color.WHITE);
        signUpButton.setBounds(770, 15, 100, 40); // 상단 오른쪽
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                new SignUpPage(); // 회원가입 페이지 열기
            }
        });
        topMenu.add(signUpButton);

        // 로그인(Login) 버튼
        Button loginButton = new Button("Login", Color.BLACK, Color.WHITE);
        loginButton.setBounds(880, 15, 100, 40); // 상단 오른쪽
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 창 닫기
                new LoginPage(); // 로그인 페이지 열기
            }
        });
        topMenu.add(loginButton);

        // 배경색 설정
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // 창 보이기
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
