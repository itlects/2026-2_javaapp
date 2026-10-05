package week03.example04;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;

public class MouseEventExample {
    private JFrame frame;
    private JPanel panel;
    private JLabel lblInfo;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MouseEventExample().frame.setVisible(true));
    }

    public MouseEventExample() {
        frame = new JFrame("예제4 - MouseEvent");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setBounds(100, 45, 280, 110);
        panel.setBackground(Color.LIGHT_GRAY);
        frame.getContentPane().add(panel);

        lblInfo = new JLabel("패널 위로 마우스를 이동하세요.");
        lblInfo.setBounds(100, 180, 320, 30);
        frame.getContentPane().add(lblInfo);

        panel.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.CYAN);
                lblInfo.setText("mouseEntered");
            }
            @Override public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.LIGHT_GRAY);
                lblInfo.setText("mouseExited");
            }
            @Override public void mouseClicked(MouseEvent e) {
                lblInfo.setText(e.getClickCount() >= 2 ? "Double Click" : "Single Click");
            }
        });
    }
}