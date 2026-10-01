package week03.example01;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ButtonEventExample {
    private JFrame frame;
    private JLabel lblMessage;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ButtonEventExample window = new ButtonEventExample();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ButtonEventExample() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("예제1 - 버튼 이벤트");
        frame.setBounds(100, 100, 450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblMessage = new JLabel("버튼을 눌러보세요.");
        lblMessage.setBounds(145, 50, 180, 30);
        frame.getContentPane().add(lblMessage);

        JButton btnClick = new JButton("클릭");
        btnClick.setBounds(165, 110, 100, 35);
        frame.getContentPane().add(btnClick);

        btnClick.addActionListener(e -> lblMessage.setText("버튼이 클릭되었습니다."));
    }
}
