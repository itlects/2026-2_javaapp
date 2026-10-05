package week03.example03;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

public class KeyFocusEventExample {
    private JFrame frame;
    private JTextField txtId;
    private JLabel lblInfo;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new KeyFocusEventExample().frame.setVisible(true));
    }

    public KeyFocusEventExample() {
        frame = new JFrame("예제3 - Key / Focus Event");
        frame.setBounds(100, 100, 500, 260);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblId = new JLabel("아이디");
        lblId.setBounds(70, 55, 70, 30);
        frame.getContentPane().add(lblId);

        txtId = new JTextField();
        txtId.setBounds(145, 55, 220, 30);
        frame.getContentPane().add(txtId);

        lblInfo = new JLabel("입력창을 선택해 보세요.");
        lblInfo.setBounds(70, 120, 360, 30);
        frame.getContentPane().add(lblInfo);

        txtId.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                lblInfo.setText("입력 글자 수: " + txtId.getText().length()
                        + (e.getKeyCode() == KeyEvent.VK_ENTER ? " / ENTER" : ""));
            }
        });

        txtId.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtId.setBackground(new Color(255, 255, 220));
                lblInfo.setText("Focus gained");
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtId.setBackground(Color.WHITE);
                if (txtId.getText().trim().isEmpty()) lblInfo.setText("아이디를 입력하세요.");
            }
        });
    }
}