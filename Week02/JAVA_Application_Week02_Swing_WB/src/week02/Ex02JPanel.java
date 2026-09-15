package week02;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Ex02JPanel {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ex02 - JPanel");
            JPanel panel = new JPanel(new FlowLayout());
            panel.setBackground(new Color(235, 245, 255));
            panel.add(new JButton("확인"));
            panel.add(new JButton("취소"));
            frame.add(panel);
            frame.setSize(420, 250);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
