package week02;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex01JFrameBasic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ex01 - JFrame 기본");
            frame.setSize(420, 250);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
