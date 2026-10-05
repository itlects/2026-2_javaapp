package week03.example01;

import java.awt.EventQueue;
import javax.swing.*;

public class ActionEventExample {
    private JFrame frame;
    private JLabel lblMessage;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new ActionEventExample().frame.setVisible(true));
    }

    public ActionEventExample() {
        frame = new JFrame("예제1 - ActionEvent");
        frame.setBounds(100, 100, 430, 230);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblMessage = new JLabel("버튼을 눌러보세요.");
        lblMessage.setBounds(130, 45, 220, 30);
        frame.getContentPane().add(lblMessage);

        JButton btnRun = new JButton("실행");
        btnRun.setBounds(95, 105, 100, 35);
        frame.getContentPane().add(btnRun);

        JButton btnClear = new JButton("초기화");
        btnClear.setBounds(215, 105, 100, 35);
        frame.getContentPane().add(btnClear);

        btnRun.addActionListener(e -> lblMessage.setText("ActionEvent 발생 → 실행"));
        btnClear.addActionListener(e -> lblMessage.setText("버튼을 눌러보세요."));
    }
}