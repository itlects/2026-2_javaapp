package week02;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Ex01JFrameBasic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("첫 번째 Swing 창");
            frame.setSize(420, 260);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
