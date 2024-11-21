import frontend.page.MainFrame;

public class Main {
    public static void main(String[] args) {
        // 메인프레임 실행
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(); // MainFrame 인스턴스 생성
            mainFrame.setVisible(true); // 창을 보이게 설정
        });
    }
}